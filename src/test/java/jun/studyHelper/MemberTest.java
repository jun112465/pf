package jun.studyHelper;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

@EnableAutoConfiguration(exclude =  {DataSourceAutoConfiguration.class})
public class MemberTest {

    @Test
    public void deleteFriend(){
//        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
//        MemberRepository mr = ac.getBean(MemberRepository.class);
        System.out.println("test good");

    }

}
