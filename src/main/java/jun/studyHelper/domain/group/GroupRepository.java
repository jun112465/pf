package jun.studyHelper.domain.group;

import jun.studyHelper.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface GroupRepository {

    void create(Group group);

    void delete(Group group);

    void updateName(Group group);

    Group findById(String id);

    List<Group> findAll();
    List<Group> findAll(Group search);
}
