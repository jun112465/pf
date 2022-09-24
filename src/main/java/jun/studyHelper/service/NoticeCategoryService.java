package jun.studyHelper.service;

import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.repository.noticeCategory.NoticeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NoticeCategoryService {

    NoticeCategoryRepository ncr;

    @Autowired
    public NoticeCategoryService(NoticeCategoryRepository ncr) {
        this.ncr = ncr;
    }

    public boolean addCategory(NoticeCategory nc){
        if(validateCategory(nc)){
            ncr.save(nc);
            return true;
        }

        System.err.println("중복된 카테고리 ");
        return false;
    }

    public List<NoticeCategory> getCategories(String memberId){
        return ncr.findByMemberId(memberId);
    }

    public boolean validateCategory(NoticeCategory nc){
        List<NoticeCategory> noticeCategories = getCategories(nc.getMemberId());
        for(NoticeCategory nc2 : noticeCategories){
            if (nc2.getCategory().equals(nc.getCategory()))
                return false;
        }
        return true;
    }

    public void deleteCategory(int id){
        ncr.deleteById(id);
    }
}
