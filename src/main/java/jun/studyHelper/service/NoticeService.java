package jun.studyHelper.service;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.repository.notice.NoticeRepository;
import jun.studyHelper.repository.noticeCategory.NoticeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NoticeService {
    NoticeRepository noticeRepository;
    NoticeCategoryRepository noticeCategoryRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, NoticeCategoryRepository noticeCategoryRepository){
        this.noticeRepository = noticeRepository;
        this.noticeCategoryRepository = noticeCategoryRepository;
    }
    public Notice add(Notice notice, Member member){

        if(isTodayNoticeAdded(member, notice))
            return null;

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

    @Modifying
    @Transactional
    public void updateCategories(NoticeCategory noticeCategory){
        Member m = new Member();
        m.setId(noticeCategory.getMemberId());
        List<Notice> notices = noticeRepository.findByMemberId(m);
        for (Notice n : notices){
            if (n.getId() == noticeCategory.getId())
                n.setCategory(noticeCategory.getCategory());
        }

        NoticeCategory nc = noticeCategoryRepository.findById(noticeCategory.getId());
        nc.setCategory(noticeCategory.getCategory());
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

    public Map<String, List<Notice>> getGroupedNoticeList(Member member){
        List<Notice> nl = findMemberNoticeList(member);

        Map<String, List<Notice>> ret = new HashMap<>();
        for (Notice n : nl){
            if (!ret.containsKey(n.getCategory()))
                ret.put(n.getCategory(), new ArrayList<>());
            ret.get(n.getCategory()).add(n);
        }

        return ret;
    }

    public boolean isTodayNoticeAdded(Member member, Notice notice){

        String now = LocalDate.now().toString();
        // 이미 해당하는 날짜에 만들어진 notice 가 없다면 false 반환

        System.out.println("LOG : findRecentMemberNotice : " + noticeRepository.findRecentMemberNotice(member, notice));

        if (noticeRepository.findRecentMemberNotice(member, notice) == null)
            return false;
        // 반대의 경우 true 반환
        else
            return true;
    }
}
