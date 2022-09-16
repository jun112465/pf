package jun.studyHelper.domain.member;

import jun.studyHelper.domain.entity.Member;

import java.util.List;

public interface FriendRepository {

    void add(Member me, Member friend);
    void delete(Member me, Member friend);
    List<Member> findAll(Member me);
}
