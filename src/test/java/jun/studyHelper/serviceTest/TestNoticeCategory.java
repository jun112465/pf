package jun.studyHelper.serviceTest;

import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.repository.noticeCategory.NoticeCategoryRepository;
import jun.studyHelper.service.NoticeCategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
//@DataJpaTest
public class TestNoticeCategory {

    @Autowired
    NoticeCategoryRepository ncr;

    @Autowired
    NoticeCategoryService ncs;

    @Test
    @DisplayName("카테고리 생성 시 중복 확인")
    public void test0(){
        //given
        NoticeCategory nc = new NoticeCategory();
        nc.setMemberId("testId");
        nc.setCategory("testCategory");
        ncr.save(nc);

        NoticeCategory nc1 = new NoticeCategory();
        nc1.setCategory("testCategory");
        nc1.setMemberId("testId");

        //when
        String res="";
        List<NoticeCategory> l = ncs.getCategories("testId");
        for (NoticeCategory nc2 : l){
            if (nc.getCategory() == nc1.getCategory()) {
                res = "중복";
                return;
            }
        }

        //then
        Assertions.assertThat(res).isEqualTo("중복");
    }

    @Test
    @DisplayName("카테고리 중복 생성 시 실패확인")
    public void test1(){
        //given
        NoticeCategory nc1 = new NoticeCategory();
        nc1.setCategory("testCategory");
        nc1.setMemberId("testId");

        NoticeCategory nc2 = new NoticeCategory();
        nc2.setCategory("testCategory");
        nc2.setMemberId("testId");

        //when


        //then
//        Assertions.assertThat(res).isFalse();
    }


}
