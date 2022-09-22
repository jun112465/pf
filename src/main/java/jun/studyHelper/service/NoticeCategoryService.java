package jun.studyHelper.service;

import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.repository.noticeCategory.NoticeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    public List<String> getCategories(String memberId){
        List<NoticeCategory> l = ncr.findByMemberId(memberId);
        List<String> rtnList = new ArrayList<>();

        for (NoticeCategory _l : l){
            rtnList.add(_l.getCategory());
        }

        return rtnList;
    }

    public boolean validateCategory(NoticeCategory nc){
        return getCategories(nc.getMemberId())
                .contains(nc.getCategory());
    }

    public void deleteCategory(NoticeCategory nc){
        ncr.delete(nc);
    }
}
