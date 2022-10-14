package jun.studyHelper.repository;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.repository.notice.NoticeRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NoticeTest {

    @Autowired
    NoticeRepo nr;

    @Test
    @DisplayName("문서 생성하기")
    public void test0(){

        //given
        Notice n1 = new Notice();
        n1.setCategoryId(1);

        //when
        nr.saveAndFlush(n1);

        //then
//        System.out.println(nr.findByMemberId("1"));
    }

    @Test
    @DisplayName("문서 생성 후 수정 테스트")
    public void test1(){
        //given
        Notice n1 = new Notice();
        nr.save(n1);
        Assertions.assertThat(n1.getContent()).isEqualTo("write 1");

        //when
        n1.setContent("write 2");

        //then
    }

}
