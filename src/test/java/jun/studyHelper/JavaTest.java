package jun.studyHelper;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class JavaTest {


    @Value("${spring.datasource.username}")
    private String URL;

    @Test
    public void appPropertiesTest(){
        System.out.println(URL);
    }
}
