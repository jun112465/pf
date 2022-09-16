package jun.studyHelper.domain.group;

import java.util.List;


public interface GroupRepository {

    void create(Group group);

    void delete(Group group);

    void updateName(Group group);

    Group findById(String id);

    List<Group> findAll();
    List<Group> findAll(Group search);
}
