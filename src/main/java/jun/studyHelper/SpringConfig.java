package jun.studyHelper;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.MemberRepository;
import jun.studyHelper.domain.member.MemoryMemberRepository;
import jun.studyHelper.domain.notice.JdbcNoticeRepository;
import jun.studyHelper.domain.notice.MemoryNoticeRepository;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeRepository;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {


    DataSource dataSource;

    public void SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    NoticeService noticeService(){
        NoticeService ns =  new NoticeService(noticeRepository());
        return ns;
    }

    @Bean
    NoticeRepository noticeRepository(){
//        return new MemoryNoticeRepository();
        return new JdbcNoticeRepository();
    }

    @Bean
    MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    MemberRepository memberRepository(){
        return new JdbcMemberRepository();
//        return new MemoryMemberRepository();
    }
}
