package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    CategoryRepository categoryRepository;
    MemberService memberService;

    NoticeService noticeService;



    @Autowired
    public CategoryService(CategoryRepository categoryRepository, MemberService memberService, NoticeService noticeService) {
        this.categoryRepository = categoryRepository;
        this.memberService = memberService;
        this.noticeService = noticeService;
    }

    public Category findCategory(CategoryDTO categoryDTO){
        return categoryRepository.findById(categoryDTO.getId()).orElse(null);
    }

    /**
     * 성공하면 entity 반환
     * 실패 시 null 반환
     *
     * @param categoryDTO
     * @return
     */
    public Category addCategory(CategoryDTO categoryDTO){
        Category category = null;
        if(validateCategory(categoryDTO)){
            Member member = memberService.findMember(MemberDTO
                    .builder()
                    .id(categoryDTO.getMemberId())
                    .build())
                    .orElse(null);

            category = categoryRepository.save(Category.builder()
                    .name(categoryDTO.getName())
                    .member(member)
                    .build());
        }

        return category;
    }

    public List<Category> getCategories(MemberDTO memberDTO){
        return categoryRepository.findAllByMemberId(memberDTO.getId());
    }


    public boolean validateCategory(CategoryDTO categoryDTO){
        List<Category> noticeCategories =categoryRepository.findAllByMemberId(categoryDTO.getMemberId());
        for(Category c : noticeCategories){
            if (c.getName().equals(categoryDTO.getName()))
                return false;
        }

        return true;
    }


    public void deleteCategory(CategoryDTO categoryDTO){
        noticeService.deleteNoticeListByCategory(categoryDTO);
        categoryRepository.deleteById(categoryDTO.getId());
//        noticeService.deleteNoticeListByCategory(categoryDTO.);
//        noticeService.deleteNoticeListByCategory(categoryDTO);
//        ncr.deleteById(id);
    }

    public Category findByMemberAndName(CategoryDTO categoryDTO){
        return categoryRepository
                .findCategoryByMemberIdAndName(categoryDTO.getMemberId(), categoryDTO.getName())
                .orElse(null);
    }



}
