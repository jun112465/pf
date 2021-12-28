package jun.studyHelper;

import jun.studyHelper.domain.member.MemberRepository;
import jun.studyHelper.domain.member.MemoryMemberRepository;
import jun.studyHelper.domain.notice.MemoryNoticeRepository;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeRepository;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigBean {

    @Bean
    NoticeService noticeService(){
        NoticeService ns =  new NoticeService(noticeRepository());
        System.out.println("AppConfig : " + ns);
        return ns;
    }

    @Bean
    NoticeRepository noticeRepository(){ return new MemoryNoticeRepository(); }

    @Bean
    MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
