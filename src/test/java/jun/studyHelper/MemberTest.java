package jun.studyHelper;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.member.MemberRepository;
import jun.studyHelper.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    JdbcMemberRepository mr;

    @Autowired
    MemberService ms;

    @Test
    public void deleteFriend(){
//        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
//        MemberRepository mr = ac.getBean(MemberRepository.class);
        System.out.println("test good");

    }

    @Test
    public void findMember(){
        System.out.println(mr.findAll());
    }

    @Test
    public void addMember() throws Exception{
        //Given
        Member member = new Member("testID");
        member.setPassword("testPW");
        ms.join(member);

        //When

        //Then
        System.out.println(mr.findAll());
    }

}
