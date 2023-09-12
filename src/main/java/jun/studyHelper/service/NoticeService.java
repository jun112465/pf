package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.repository.notice.NoticeRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class NoticeService {
    NoticeRepository noticeRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository, CategoryRepository categoryRepository){
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<Notice> add(NoticeDTO noticeDTO){

        return Optional.ofNullable(noticeRepository.save(Notice.builder()
                .user(userRepository.findById(noticeDTO.getUserId()).orElse(null))
                .category(categoryRepository.findById(noticeDTO.getCategoryId()).orElse(null))
                .content(noticeDTO.getContent())
                .date(noticeDTO.getDate())
                .build()));
    }

    public void delete(Long noticeId){
        noticeRepository.deleteById(noticeId);
    }

    public Notice findNotice(NoticeDTO noticeDTO){
        return noticeRepository.findById(noticeDTO.getNoticeId()).orElse(null);
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
    public void updateCategories(Category noticeCategory){
        Category nc = categoryRepository.findById(noticeCategory.getId()).get();
        nc.setName(noticeCategory.getName());
    }

    //쿼리

    public void deleteNoticeListByCategory(CategoryDTO categoryDTO){
        noticeRepository.deleteAllByCategory(
                Category.builder()
                    .id(categoryDTO.getId())
                    .build()
        );
    }

    public List<Notice> findNoticeList(){
        return noticeRepository.findAll();
    }

    public List<Notice> findMemberNoticeList(UserDTO userDTO){
        User user = userRepository.findById(userDTO.getId()).get();
        return noticeRepository.findByUserIdOrderByDateAsc(user.getId());
    }

    public Map<Category, List<Notice>> getNoticeListGroupedByCategory(UserDTO userDTO){

        List<Category> noticeCategories = categoryRepository.findAllByUserId(userDTO.getId());
        Map<Category, List<Notice>> noticeCategoryListMap = new HashMap<>();
        for(Category nc : noticeCategories){
            noticeCategoryListMap.put(nc, noticeRepository.findByCategoryOrderByDateAsc(nc));
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
        List<Notice> noticeList = noticeRepository.findByCategoryOrderByDateAsc(notice.getCategory());
        if (noticeList.size() > 0) {
            String recentNoteDate = String.valueOf(noticeList.get(0).getDate());
            return notice.getDate().equals(recentNoteDate);
        }

        return false;
    }
}
