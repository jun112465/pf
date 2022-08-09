package jun.studyHelper.DB;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class GroupTest {

    @Autowired
    GroupService gs;

    @Test
    void 그룹생성(){
        String id = String.valueOf(UUID.randomUUID());
        String name = "testGroup";
        Group group = new Group(id, name);
//        gs.createGroup(group);
    }

    @Test
    void 그룹삭제(){
        String id = String.valueOf(UUID.randomUUID());
        String name = "testGroup";
        Group group = new Group(id, name);
        gs.deleteGroup(group);
    }

    @Test
    void 그룹이름변경(){
        String id = String.valueOf(UUID.randomUUID());
        String name = "testGroup";
        Group group = new Group(id, name);
//        gs.createGroup(group);

        group.setName("newTestGroup");
        gs.updateGroupName(group);
    }

    @Test
    void 그룹찾기(){
        Group group = new Group("test", "test");
        System.out.println(gs.searchGroups(group));
    }
}
