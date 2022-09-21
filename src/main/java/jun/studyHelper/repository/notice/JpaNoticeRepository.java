package jun.studyHelper.repository.notice;


import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaNoticeRepository implements NoticeRepository{

    private final EntityManager em;

    public JpaNoticeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Notice save(Notice notice) {
        em.persist(notice);
        return notice;
    }

    @Override
    public Notice findById(int id) {
        Query q = em.createQuery("SELECT n FROM notice n WHERE n.id=:id")
                .setParameter("id", id);
        return (Notice) q.getSingleResult();
    }

    @Override
    public Notice update(Notice notice) {
        em.createQuery("UPDATE notice n SET n.content=:content WHERE n.id=:id")
                .setParameter("content", notice.getContent())
                .setParameter("id", notice.getId());
        return notice;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Notice> findAll() {
        return null;
    }

    @Override
    public List<Notice> findByMemberId(Member member) {
        Query q = em.createQuery("SELECT n FROM notice n WHERE n.memberId=:memberId ORDER BY n.date DESC ")
                .setParameter("memberId", member.getId());

        return q.getResultList();
    }

    @Override
    public Notice findRecentMemberNotice(Member member, String date) {
        return null;
    }
}
