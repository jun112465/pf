package jun.studyHelper.repository.noticeCategory;

import jun.studyHelper.domain.entity.NoticeCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class JpaNoticeCategoryRepository implements NoticeCategoryRepository{

    EntityManager em;

    public JpaNoticeCategoryRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public void save(NoticeCategory noticeCategory) {
        em.persist(noticeCategory);
    }

    @Override
    public NoticeCategory findById(int id){
        return em.find(NoticeCategory.class, id);
    }
    @Override
    public void update(NoticeCategory noticeCategory){
        em.createQuery("UPDATE NoticeCategory nc SET nc.category=:category WHERE nc.id=:id")
                .setParameter("category", noticeCategory.getCategory())
                .setParameter("id", noticeCategory.getId());
    }

    @Override
    public void delete(NoticeCategory noticeCategory) {
        em.remove(noticeCategory);
    }

    @Override
    public void deleteById(int id){
        em.remove(em.find(NoticeCategory.class, id));
    }

    @Override
    public List<NoticeCategory> findByMemberId(String memberId) {

        Query q = em.createQuery("select nc from NoticeCategory nc where nc.memberId=:memberId")
                .setParameter("memberId", memberId);

        return q.getResultList();
    }
}