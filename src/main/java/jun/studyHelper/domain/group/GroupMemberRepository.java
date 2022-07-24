package jun.studyHelper.domain.group;

import jun.studyHelper.domain.member.Member;

import java.util.List;

public interface GroupMemberRepository {

    void add(Member member, Group group);
    void delete(Member member, Group group);
    List<Member> findAllMember(Group group);
    List<Group> findAllGroups(Member member);
}
