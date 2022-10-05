package jun.studyHelper.repository.noticeCategory;

import jun.studyHelper.entity.NoticeCategory;

import java.util.List;

public interface NoticeCategoryRepository {

    void save(NoticeCategory noticeCategory);
    void update(NoticeCategory noticeCategory);
    void delete(NoticeCategory noticeCategory);
    void deleteById(int id);
    List<NoticeCategory> findByMemberId(String memberId);
    NoticeCategory findById(int id);

}
