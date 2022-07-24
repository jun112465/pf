package jun.studyHelper.domain.group;

import jun.studyHelper.domain.member.Member;

import java.util.List;



public interface GroupRepository {

    void create(Group group);

    Group findById(String id);

    List<Group> findAll();

    void addMember(String groupId, Member member);
}
