package jun.studyHelper.repository.noticeCategory;

import jun.studyHelper.domain.entity.NoticeCategory;

import java.util.List;

public interface NoticeCategoryRepository {

    void save(NoticeCategory noticeCategory);
    void delete(NoticeCategory noticeCategory);
    List<NoticeCategory> findByMemberId(String memberId);
}
