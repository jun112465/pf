package jun.studyHelper.serviceTest;

import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.repository.noticeCategory.NoticeCategoryRepository;
import jun.studyHelper.service.NoticeCategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    @DisplayName("카테고리 생성")
    public void test0(){

        //given
        NoticeCategory nc = new NoticeCategory();
        nc.setCategory("testCategory");
        nc.setMemberId("testId");

        //when
        ncs.addCategory(nc);

        //then
        List<String> l = ncs.getCategories("testId");
        String category = l.get(0);
        Assertions.assertThat(category).isEqualTo("testCategory");
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
        ncs.addCategory(nc1);
        boolean res = ncs.addCategory(nc2);

        //then
        Assertions.assertThat(res).isFalse();
    }


}
