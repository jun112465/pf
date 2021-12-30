package jun.studyHelper.dbTest;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;

public class MysqlQueryTest {

    @Test
    @DisplayName("testing findAll")
    public void getAllMembers() throws ClassNotFoundException {
        MemberService ms = new MemberService(new JdbcMemberRepository());
        List<Member> members =  ms.findMembers();

        Iterator<Member> it = members.iterator();
        while(it.hasNext()){
            Member m = it.next();
            System.out.println(m.getId()+" : "+m.getName());
        }
    }

    @Test
    @DisplayName("testing add Member")
    public void addMember(){
        MemberService ms = new MemberService(new JdbcMemberRepository());
        Member member = new Member();
        member.setId(12345678);
        member.setName("정정정");
        ms.join(member);
    }
}
