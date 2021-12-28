package jun.studyHelper.ConfigTest;

import jun.studyHelper.AppConfigBean;
import jun.studyHelper.controller.MemberController;
import jun.studyHelper.domain.member.MemberRepository;
import jun.studyHelper.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppConfigTest {

    @Test
    @DisplayName("DI 테스트하기")
    void testAppConfigDi(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigBean.class);

        MemberController mc = ac.getBean("memberController", MemberController.class);
        MemberService ms = ac.getBean("memberService", MemberService.class);
        MemberRepository mr = ac.getBean("memberRepository", MemberRepository.class);


        System.out.println(ms.memberRepository);
        System.out.println(mr);
        Assertions.assertThat(mc.memberService).isSameAs(ms);
        Assertions.assertThat(ms.memberRepository).isSameAs(mr);
    }
}
