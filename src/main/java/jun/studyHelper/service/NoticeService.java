package jun.studyHelper.service;

import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void editNote(Notice notice){
        noticeRepository.update(notice);
    }

    public void delete(int id){
        noticeRepository.remove(id);
    }

    public List<Notice> findNoticeList(){
        return noticeRepository.findAll();
    }

    public List<Notice> findMemberNoticeList(Member member){
        return noticeRepository.findByMemberId(member.getMemberId());
    }

    public boolean isTodayNoticeAdded(Member member){

        String now = LocalDate.now().toString();
        // 이미 해당하는 날짜에 만들어진 notice 가 없다면 false 반환
        if (noticeRepository.findRecentMemberNotice(member, now) == null)
            return false;
        // 반대의 경우 true 반환
        else
            return true;
    }
}
