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
        Query q = em.createQuery("SELECT n FROM Notice n WHERE n.id=:id")
                .setParameter("id", id);
        return (Notice) q.getSingleResult();
    }

    @Override
    public Notice update(Notice notice) {
        em.createQuery("UPDATE Notice n SET n.content=:content WHERE n.id=:id", Notice.class)
                .setParameter("content", notice.getContent())
                .setParameter("id", notice.getId());
        return notice;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Notice> findByCategoryId(int categoryId){
        return em.createQuery("select n from Notice n where n.categoryId=:categoryId", Notice.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
    @Override
    public List<Notice> findAll() {
        return null;
    }

    @Override
    public List<Notice> findByMemberId(Member member) {
        Query q = em.createQuery("SELECT n FROM Notice n WHERE n.memberId=:memberId ORDER BY n.date DESC ")
                .setParameter("memberId", member.getId());

        return q.getResultList();
    }

    @Override
    public Notice findRecentMemberNotice(Member member, Notice notice) {

        Query q = em.createQuery("SELECT n FROM Notice n WHERE n.date=:date AND n.categoryId=:categoryId")
                .setParameter("date", notice.getDate())
                .setParameter("categoryId", notice.getCategoryId());

        List<Notice> li = q.getResultList();
        if (li.size() > 0)
            return li.get(0);

        return null;
    }
}
