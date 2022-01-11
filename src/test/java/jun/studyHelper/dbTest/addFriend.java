package jun.studyHelper.dbTest;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.service.MemberService;
import org.junit.jupiter.api.Test;

public class addFriend {

    @Test
    public void testingAddFriend(){
        MemberService ms = new MemberService(new JdbcMemberRepository());

        Member me = new Member();
        me.setMemberId(18011646);

        Member fr = new Member();
        fr.setMemberId(20220108);

        ms.addFriend(me, fr);

    }
}
