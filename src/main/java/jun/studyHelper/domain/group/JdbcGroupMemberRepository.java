package jun.studyHelper.domain.group;

import jun.studyHelper.domain.member.Member;

import java.util.List;

public class JdbcGroupMemberRepository implements GroupMemberRepository{

    @Override
    public void add(Member member, Group group) {

    }

    @Override
    public void delete(Member member, Group group) {

    }

    @Override
    public List<Member> findAllMember(Group group) {

        return null;
    }

    @Override
    public List<Group> findAllGroups(Member member) {

        return null;
    }
}
