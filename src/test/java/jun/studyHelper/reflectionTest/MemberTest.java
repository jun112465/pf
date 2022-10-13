package jun.studyHelper.reflectionTest;

import jun.studyHelper.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    @DisplayName("Member 의 Reflection 가져오기")
    public void test0(){
        Class clazz = Member.class;

        System.out.println(clazz.getName());
    }
}
