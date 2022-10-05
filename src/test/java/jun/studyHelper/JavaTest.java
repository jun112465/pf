package jun.studyHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JavaTest {

    public static void main(String[] args) {
        String[] test = new String[3];

        test[0] = "abc";
        test[1] = "def";
        test[2] = "ghi";

        System.out.println(Arrays.stream(test).collect(Collectors.toList()).indexOf("def"));

    }
}
