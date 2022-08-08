package jun.studyHelper;

import jun.studyHelper.domain.group.Group;

import java.time.LocalDate;

public class JavaTest {

    public static void main(String[] args) {
        Group group = new Group();

        System.out.println(group.createGroupId());
    }
}
