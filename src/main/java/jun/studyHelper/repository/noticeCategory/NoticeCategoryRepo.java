package jun.studyHelper.repository.noticeCategory;

import jun.studyHelper.entity.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeCategoryRepo extends JpaRepository<NoticeCategory, Integer> {

    @Override
    List<NoticeCategory> findAll();

    @Override
    <S extends NoticeCategory> S save(S entity);

    @Override
    void deleteById(Integer integer);

    // Query
    List<NoticeCategory> findByMemberId(String memberId);
}
