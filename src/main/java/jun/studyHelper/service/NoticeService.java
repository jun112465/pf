package jun.studyHelper.service;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.repository.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NoticeService {
    NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }
    public Notice add(Notice notice){
        return noticeRepository.save(notice);
    }

    public Notice findNotice(int id){
        return noticeRepository.findById(id);
    }

    @Modifying
    @Transactional
    public void editNote(Notice notice){
        Notice tmp = noticeRepository.findById(notice.getId());
        tmp.setContent(notice.getContent());
//        noticeRepository.update(notice);
    }

    public void delete(int id){
        noticeRepository.remove(id);
    }

    public List<Notice> findNoticeList(){
        return noticeRepository.findAll();
    }

    public List<Notice> findMemberNoticeList(Member member){
        return noticeRepository.findByMemberId(member);
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
