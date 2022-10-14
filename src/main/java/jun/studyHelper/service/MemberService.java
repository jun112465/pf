package jun.studyHelper.service;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.repository.member.JpaMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    public JpaMemberRepo memberRepository;

    @Autowired
    public MemberService(JpaMemberRepo memberRepository){
        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입을 진행하는 메서드.
     *
     * @param member
     * @return
     */
    public Member join(Member member) {
        if(!validateDuplicateMember(member))
            return null;

        return memberRepository.save(member);
    }

    /**
     * Uid를 갖고있는 member 가 이미 있는지 검증하는 메소드
     * 멤버가 없으면 -> true 반환
     * 멤버가 있으면 -> false 반환
     *
     * @param member
     * @return
     */
    private boolean validateDuplicateMember(Member member) {
        return memberRepository.findByUid(member.getUid()).isEmpty();
    }

    public void deleteMember(Member member){
        memberRepository.delete(member);
    }


    /**
     * 회원가입 과정에서 폼으로 빈 데이터가 들어오는 것을 방지하는 코드다
     * uid or pw 가 비어있으면 true 반환
     * 둘 다 값이 있으면 false 반환
     * @param member
     * @return
     */
    public boolean isMemberBlank(Member member){
        if (member.getUid().isBlank() || member.getPw().isBlank()){
            return true;
        }
        return false;
    }


    /**
     *
     * 매개변수로 들어온 member 와 find 된 member의 일치 여부를 확인해준다.
     *
     * 찾지못하거나 uid or pw 가 일치하지 않으면 false 반환
     * uid 와 pw 가 일치하면 true 반환
     *
     * @param member
     * @return
     */
    public boolean validateMemberInfo(Member member){
        Optional<Member> find = memberRepository.findById(member.getId());

        if (find.isEmpty())
            return false;

        return find.equals(member);
    }



    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberByUId(Member member) {
        return Optional.ofNullable(
                memberRepository
                        .findByUid(member.getUid())
                        .get(0)
        );
    }
}


