package jun.studyHelper;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QueryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void getFriendList(){
        System.out.println(memberRepository.findAll().toString());
        System.out.println("Hello query test");
    }

}
