package jun.studyHelper.service;

import jun.studyHelper.domain.Member;
import jun.studyHelper.domain.MemberRepository;
import jun.studyHelper.domain.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private MemberRepository memberRepository;

    MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    /**
     * 회원가입
     */
    public int join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(int memberId) {
        return memberRepository.findById(memberId);
    }
}
