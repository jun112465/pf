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
     * 회원가입
     */
    public boolean join(Member member) {
        // 성공 -> true 반환
        // 실패 -> false 반환
        if (!validateDuplicateMember(member)){
            return false;
        }; //중복 회원 검증
        memberRepository.save(member);
        return true;
    }
    private boolean validateDuplicateMember(Member member) {
        if(memberRepository.findById(member.getId()).isPresent())
            return false;
        return true;
    }

    public void deleteMember(Member member){
        memberRepository.delete(member);
    }

    public boolean isMemberNull(Member member){
        if (member.getId().isBlank() || member.getPw().isBlank()){
            return false;
        }
        return true;
    }


    public Member validateMemberInfo(Member member){
        Optional<Member> find = findOne(member.getId());
        return find.orElse(null);
    }



    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String memberId) {
        return memberRepository.findById(memberId);
    }

    public void addFriend(Member me, Member friend){
//        memberRepository.addFriend(me,friend);
    }
}


