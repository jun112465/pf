package jun.studyHelper.repository.noticeCategory;

import jun.studyHelper.domain.entity.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeCategoryRepo extends JpaRepository<NoticeCategory, Long> {

    @Override
    List<NoticeCategory> findAll();

    @Override
    <S extends NoticeCategory> S save(S entity);

    @Override
    void deleteById(Long id);

    // Query
    List<NoticeCategory> findByMemberId(Long memberId);
}
