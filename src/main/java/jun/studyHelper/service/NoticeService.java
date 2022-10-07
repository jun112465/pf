package jun.studyHelper.service;

import jun.studyHelper.entity.Member;
import jun.studyHelper.entity.Notice;
import jun.studyHelper.entity.NoticeCategory;
import jun.studyHelper.repository.notice.NoticeRepo;
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
//    NoticeRepository noticeRepository;
    NoticeRepo noticeRepository;
    NoticeCategoryRepository noticeCategoryRepository;

    @Autowired
    public NoticeService(NoticeRepo noticeRepository, NoticeCategoryRepository noticeCategoryRepository){
        this.noticeRepository = noticeRepository;
        this.noticeCategoryRepository = noticeCategoryRepository;
    }
    public Notice add(Notice notice, Member member){

        if(isTodayNoticeAdded(member, notice))
            return null;

        return noticeRepository.save(notice);
    }

    public Notice findNotice(int id){
        return noticeRepository.findById(id).get();
    }


    @Modifying
    @Transactional
    public void editNote(Notice notice){
        Notice tmp = noticeRepository.findById(notice.getId()).get();
        tmp.setContent(notice.getContent());
//        noticeRepository.update(notice);
    }

    @Modifying
    @Transactional
    public void updateCategories(NoticeCategory noticeCategory){
        NoticeCategory nc = noticeCategoryRepository.findById(noticeCategory.getId());
        nc.setCategory(noticeCategory.getCategory());
    }


//    public void delete(Notice notice){
//        noticeRepository.remove(notice);
//    }

    public void deleteNoticeListByCategory(Member member, NoticeCategory noticeCategory){
        List<Notice> notices = findMemberNoticeList(member);
        for(Notice n : notices){
            if (n.getCategoryId() == noticeCategory.getId())
                noticeRepository.deleteById(n.getId());
        }
    }

    public List<Notice> findNoticeList(){
        return noticeRepository.findAll();
    }

    public List<Notice> findMemberNoticeList(Member member){
        return noticeRepository.findByMemberId(member.getId());
    }

    public Map<NoticeCategory, List<Notice>> getGroupedNoticeList(Member member){

        List<NoticeCategory> noticeCategories = noticeCategoryRepository.findByMemberId(member.getId());

        Map<NoticeCategory, List<Notice>> ret = new HashMap<>();
        for(NoticeCategory nc : noticeCategories){
            ret.put(nc, new ArrayList<>());
            ret.put(nc, noticeRepository.findByCategoryId(nc.getId()));
        }

        return ret;
    }

    public boolean isTodayNoticeAdded(Member member, Notice notice){

        String now = LocalDate.now().toString();
        // 이미 해당하는 날짜에 만들어진 notice 가 없다면 false 반환

//        System.out.println("LOG : findRecentMemberNotice : " + noticeRepository.findRecentMemberNotice(member, notice));

        List<Notice> noticeList = noticeRepository.findByMemberId(member.getId());

        if(noticeList.get(0).getDate().equals(now))
            return false;
        // 반대의 경우 true 반환
        else
            return true;
    }
}
