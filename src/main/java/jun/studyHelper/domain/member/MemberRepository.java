package jun.studyHelper.domain.member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member findById(String id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    Map<Integer, String> getFriends(Member me);

    void updateMemberInfo(String profileFileName, String profileMessage, int memberId);
}
