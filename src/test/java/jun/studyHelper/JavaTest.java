package jun.studyHelper;

import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class JavaTest {

    public static void main(String[] args) {
        int a = 4;
        System.out.println("변수 : " + a);
        System.out.println("리터럴 : " + 4);

        String b = "string variable";
        System.out.println(b);
        System.out.println("리터럴 스트링");
    }
}
