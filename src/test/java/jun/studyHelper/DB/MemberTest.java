package jun.studyHelper.DB;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.member.MemberRepository;
import jun.studyHelper.service.MemberService;
import org.assertj.core.api.Assertions;
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
    public void loginSuccess(){

        //Given
        String id = "ktop1017";
        String pw = "jkc1073";

        //When
        Member m = new Member();
        m.setMemberId(id);
        m.setPassword(pw);

        //Then
        System.out.println(ms.validateMemberInfo(m));
    }

    @Test
    public void loginFail(){
        //Given
        String id = "failId";
        String pw = "failPw";

        //When
        Member m = new Member();
        m.setMemberId(id);
        m.setPassword(pw);

        //Then
        Member find = ms.findOne(id);
        Assertions.assertThat(find).isEqualTo(null);
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
