package jun.studyHelper.reflectionTest;

import jun.studyHelper.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("Member 의 Reflection 가져오기")
    public void test0(){
        Class clazz = User.class;

        System.out.println(clazz.getName());
    }
}
