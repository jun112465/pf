package jun.studyHelper.repository.member;

import jun.studyHelper.domain.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
public class JpaMemberRepository<M, S> implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Map<Integer, String> getFriends(Member me) {
        return null;
    }

    @Override
    public void updateMemberInfo(String profileFileName, String profileMessage, int memberId) {

    }
}
