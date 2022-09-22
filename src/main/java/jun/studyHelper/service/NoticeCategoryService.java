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

    public List<String> getCategories(String memberId){
        List<NoticeCategory> l = ncr.findByMemberId(memberId);
        List<String> rtnList = new ArrayList<>();

        for (NoticeCategory _l : l){
            rtnList.add(_l.getCategory());
        }

        return rtnList;
    }

    public boolean validateCategory(NoticeCategory nc){
        List<String> noticeCategories = getCategories(nc.getMemberId());
        for(String s : noticeCategories){
            if (s.equals(nc.getCategory()))
                return false;
        }
        return true;
    }

    public void deleteCategory(NoticeCategory nc){
        ncr.delete(nc);
    }
}
