package jun.studyHelper.domain.member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    Map<Integer, Member> store = new HashMap<>();
    int idCnt = 0;

    @Override
    public Member save(Member member) {
        store.put(member.getMemberId(), member);
        return member;
    }

    @Override
    public Member findById(int id) {
        return store.get(id);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
