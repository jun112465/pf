package jun.studyHelper.repository.notice;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;

import java.util.List;

public interface NoticeRepository {
    Notice save(Notice notice);

    Notice findById(int id);

    Notice update(Notice notice);

    void remove(int id);
    List<Notice> findAll();
    List<Notice> findByMemberId(Member member);

    Notice findRecentMemberNotice(Member member, Notice notice);
}
