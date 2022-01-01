package jun.studyHelper.PostTest;

import jun.studyHelper.SpringConfig;
import jun.studyHelper.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.Cookie;

public class LoginTest {

    @Test
    public void loginTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        int id = 18011646;
        if(memberService.findOne(id) != null) {
//            Cookie idCookie = new Cookie("memberId", String.valueOf(id));
//            idCookie.setPath("/");
//            resp.addCookie(idCookie);
//            System.out.println(idCookie.getName() +  " : " + idCookie.getValue());
            System.out.println("Successed");
        }
    }
}
