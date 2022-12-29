package jun.studyHelper.serviceTest;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestCategoryService {

    @Autowired
    MemberService ms;
    @Autowired
    NoticeService ns;

    @Test
    @DisplayName("카테고리 생성하기")
    public void createCategory(){


    }

    @Test
    @DisplayName("카테고리 삭제하기")
    public void deleteCategory(){
        ms.join(new Member("ktop1017", "jkc1073"));

    }

}
