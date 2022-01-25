package jun.studyHelper.service;

import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    public  MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    /**
     * 회원가입
     */
    public int join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getMemberId();

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

    public void addFriend(Member me, Member friend){
        memberRepository.addFriend(me,friend);
    }
    public Map<Integer, String> getFriends(Member me){
        Map<Integer, String> list = memberRepository.getFriends(me);
        return list;
    }
}
