package jun.studyHelper.service;

import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.exception.ErrorCode;
import jun.studyHelper.exception.IdDuplicateException;
import jun.studyHelper.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    public MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * DB에서 member를 갖고온다.
     *
     * @param memberDTO
     * @return
     */
    public Optional<Member> findMember(MemberDTO memberDTO){


        if(memberDTO.getId()!=0)
            return memberRepository.findById(memberDTO.getId());


        List<Member> memberList = memberRepository.findByUid(memberDTO.getUid());
        if(memberList.isEmpty()) return Optional.ofNullable(null);
        else return Optional.ofNullable(memberList.get(0));
    }



    /**
     * 회원가입을 진행하는 메서드.
     *
     * @param memberDTO
     * @return
     */
    public Member join(MemberDTO memberDTO) {
        if(isMemberBlank(memberDTO))
            throw new NoSuchElementException();
        if(!validateDuplicateMember(memberDTO))
            throw new IdDuplicateException("id duplicated", ErrorCode.ID_DUPLICATION);

        return memberRepository.save(Member.builder()
                .uid(memberDTO.getUid())
                .pw(memberDTO.getPwd())
                .build());
    }

    public void deleteMember(MemberDTO memberDTO){
        Member member;
        if((member = findMember(memberDTO).orElse(null)) != null)
            memberRepository.delete(member);
    }

    /**
     * Uid를 갖고있는 member 가 이미 있는지 검증하는 메소드
     * 멤버가 없으면 -> true 반환
     * 멤버가 있으면 -> false 반환
     *
     * @param member
     * @return
     */
    private boolean validateDuplicateMember(MemberDTO member) {
        return memberRepository.findByUid(member.getUid()).isEmpty();
    }

    public void deleteMember(Member member){
        memberRepository.delete(member);
    }


    /**
     * 회원가입 과정에서 폼으로 빈 데이터가 들어오는 것을 방지하는 코드다
     * uid or pw 가 비어있으면 true 반환
     * 둘 다 값이 있으면 false 반환
     * @param memberDTO
     * @return
     */
    public boolean isMemberBlank(MemberDTO memberDTO){
        if (memberDTO.getUid().isBlank() || memberDTO.getPwd().isBlank()){
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
    public boolean validateMemberInfo(MemberDTO member){

        Optional<Member> find = memberRepository.findOptionalByUid(member.getUid());
        return find.isPresent();

        // 동일한 uid 를 가진 유저 있는가?
//        List<Member> find = memberRepository.findByUid(member.getUid());
//        if(find.isEmpty())
//            return false;

//        return find.get(0).equals(member);
    }


    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberByUid(String uid) {
        return Optional.ofNullable(
                memberRepository
                        .findByUid(uid)
                        .get(0)
        );
    }
}


