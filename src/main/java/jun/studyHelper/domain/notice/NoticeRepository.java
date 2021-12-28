package jun.studyHelper.domain.notice;

import jun.studyHelper.domain.member.Member;

import java.util.List;

public interface NoticeRepository {
    void save(Notice notice);
    void remove(int id);
    List<Notice> findAll();
}
