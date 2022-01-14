package jun.studyHelper.dbTest;

import jun.studyHelper.AppConfig;
import jun.studyHelper.SpringConfig;
import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.service.MemberService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Test
    @DisplayName("친구 목록 출력 테스트")
    public void getFriendsListTest(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        MemberService ms = ac.getBean("memberService", MemberService.class);
        Member m = new Member();
        m.setMemberId(18011646);
        Map<Integer, String> friendList = ms.getFriends(m);

        System.out.println(friendList);
//        int i=0;
//        for(int s : list) {
//            System.out.println(i + ":" + s);
//            i++;
//        }
    }
}
