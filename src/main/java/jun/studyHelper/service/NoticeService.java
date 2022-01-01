package jun.studyHelper.service;

import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeService {
    NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }
    public void add(Notice notice){
        noticeRepository.save(notice);
    }

    public void delete(int id){
        noticeRepository.remove(id);
    }

    public List<Notice> findNoticeList(int memberId){
        return noticeRepository.findAll(memberId);
    }
}
