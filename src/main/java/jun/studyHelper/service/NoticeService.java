package jun.studyHelper.service;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.repository.notice.NoticeRepo;
import jun.studyHelper.repository.noticeCategory.NoticeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NoticeService {
    NoticeRepo noticeRepository;
    NoticeCategoryRepository noticeCategoryRepository;

    @Autowired
    public NoticeService(NoticeRepo noticeRepository, NoticeCategoryRepository noticeCategoryRepository){
        this.noticeRepository = noticeRepository;
        this.noticeCategoryRepository = noticeCategoryRepository;
    }

    public Notice add(Notice notice){
        if(isTodayNoticeAdded(notice))
            return null;
        return noticeRepository.saveAndFlush(notice);
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
        return noticeRepository.findByMemberIdOrderByDateAsc(member.getId());
    }

    public Map<NoticeCategory, List<Notice>> getNoticeListGroupedByCategory(Member member){

        List<NoticeCategory> noticeCategories = noticeCategoryRepository.findByMemberId(member.getId());

        Map<NoticeCategory, List<Notice>> noticeCategoryListMap = new HashMap<>();
        for(NoticeCategory nc : noticeCategories){
            noticeCategoryListMap.put(nc, noticeRepository.findByCategoryIdOrderByDateAsc(nc.getId()));
        }

        return noticeCategoryListMap;
    }


    /**
     *
     * @param notice
     * @return
     * 노트가 이미 추가됨 : true 반환
     * 노트가 아직 안추가됨 : false 반환
     *
     * 하루에 노트는 하나만 추가가 가능하다
     * 이미 추가된 노트가 있는지 확인해주는 메서드다.
     */
    public boolean isTodayNoticeAdded(Notice notice){
        List<Notice> noticeList = noticeRepository.findByCategoryIdOrderByDateAsc(notice.getCategoryId());
        if (noticeList.size() > 0) {
            String recentNoteDate = String.valueOf(noticeList.get(0).getDate());
            return notice.getDate().equals(recentNoteDate);
        }

        return false;
    }
}
