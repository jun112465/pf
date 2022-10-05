package jun.studyHelper.repository.notice;

import jun.studyHelper.entity.Member;
import jun.studyHelper.entity.Notice;

import java.util.List;

public interface NoticeRepository {
    Notice save(Notice notice);

    Notice findById(int id);

    Notice update(Notice notice);

    List<Notice> findAll();
    List<Notice> findByMemberId(Member member);

    void remove(Notice notice);

    List<Notice> findByCategoryId(int id);

    Notice findRecentMemberNotice(Member member, Notice notice);
}
