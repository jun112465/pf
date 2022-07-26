package jun.studyHelper.domain.group;

import jun.studyHelper.domain.member.Member;

import java.util.List;



public interface GroupRepository {

    void create(Group group);

    void delete(Group group);

    void updateName(Group group);

    Group findById(String id);

    List<Group> findAll();
}
