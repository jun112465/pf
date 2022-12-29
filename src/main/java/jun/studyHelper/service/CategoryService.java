package jun.studyHelper.service;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Category;
import jun.studyHelper.repository.category.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

//    NoticeCategoryRepository ncr;
    CategoryRepo ncr;
    NoticeService noticeService;

    @Autowired
    public CategoryService(CategoryRepo ncr, NoticeService noticeService) {
        this.ncr = ncr;
        this.noticeService = noticeService;
    }

    public Category findCategory(Category category){
        return ncr.getById(category.getId());
    }

    public boolean addCategory(Category nc){
        if(validateCategory(nc)){
            ncr.save(nc);
            return true;
        }

        System.err.println("중복된 카테고리 ");
        return false;
    }

    public List<Category> getCategories(Member member){
        return ncr.findByMember(member);
    }


    public boolean validateCategory(Category nc){
        List<Category> noticeCategories = getCategories(nc.getMember());
        for(Category nc2 : noticeCategories){
            if (nc2.equals(nc))
                return false;
        }

        return true;
    }


    public void deleteCategory(Category category, long id){
        noticeService.deleteNoticeListByCategory(category);
        ncr.deleteById(id);
    }



}
