package jun.studyHelper.service;

import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {
    NoticeRepository noticeRepository;

    @Autowired
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
