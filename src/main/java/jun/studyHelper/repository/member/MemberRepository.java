package jun.studyHelper.repository.member;

import jun.studyHelper.entity.Member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(String id);
    List<Member> findAll();
    Map<Integer, String> getFriends(Member me);

    void updateMemberInfo(String profileFileName, String profileMessage, int memberId);
}
