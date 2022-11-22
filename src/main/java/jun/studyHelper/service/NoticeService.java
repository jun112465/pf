package jun.studyHelper.service;

import jun.studyHelper.domain.dto.NoticeDTO;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.Category;
import jun.studyHelper.repository.notice.JpaNoticeRepo;
import jun.studyHelper.repository.category.CategoryRepo;
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
    JpaNoticeRepo jpaNoticeRepository;
    CategoryRepo categoryRepository;

    @Autowired
    public NoticeService(JpaNoticeRepo jpaNoticeRepository, CategoryRepo categoryRepository){
        this.jpaNoticeRepository = jpaNoticeRepository;
        this.categoryRepository = categoryRepository;
    }

    public Notice add(Notice notice){
        return jpaNoticeRepository.saveAndFlush(notice);
    }
    public Notice add(NoticeDTO noticeDTO){
        Notice notice = new Notice();
        return jpaNoticeRepository.saveAndFlush(notice);
    }

    public Notice findNotice(long id){
        return jpaNoticeRepository.findById(id).get();
    }


    @Modifying
    @Transactional
    public void editNote(Notice notice){
        Notice tmp = jpaNoticeRepository.findById(notice.getId()).get();
        tmp.setContent(notice.getContent());
//        noticeRepository.update(notice);
    }

    @Modifying
    @Transactional
    public void updateCategories(Category noticeCategory){
        Category nc = categoryRepository.findById(noticeCategory.getId()).get();
        nc.setName(noticeCategory.getName());
    }

    public void deleteNoticeListByCategory(Member member, Category noticeCategory){
        List<Notice> notices = findMemberNoticeList(member);
        for(Notice n : notices){
            if (n.getCategory().equals(noticeCategory))
                jpaNoticeRepository.deleteById(n.getId());
        }
    }

    public List<Notice> findNoticeList(){
        return jpaNoticeRepository.findAll();
    }

    public List<Notice> findMemberNoticeList(Member member){
        return jpaNoticeRepository.findByMemberIdOrderByDateAsc(member.getId());
    }

    public Map<Category, List<Notice>> getNoticeListGroupedByCategory(Member member){

        List<Category> noticeCategories = categoryRepository.findByMemberId(member.getId());

        Map<Category, List<Notice>> noticeCategoryListMap = new HashMap<>();
        for(Category nc : noticeCategories){
            noticeCategoryListMap.put(nc, jpaNoticeRepository.findByCategoryOrderByDateAsc(nc));
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
        List<Notice> noticeList = jpaNoticeRepository.findByCategoryOrderByDateAsc(notice.getCategory());
        if (noticeList.size() > 0) {
            String recentNoteDate = String.valueOf(noticeList.get(0).getDate());
            return notice.getDate().equals(recentNoteDate);
        }

        return false;
    }
}
