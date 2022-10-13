package jun.studyHelper.repository;

import jun.studyHelper.entity.Member;
import jun.studyHelper.entity.Notice;
import jun.studyHelper.repository.notice.NoticeRepo;
import jun.studyHelper.repository.notice.NoticeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        n1.setMemberId("1");

        //when
        nr.saveAndFlush(n1);

        //then
        System.out.println(nr.findByMemberId("1"));
    }

    @Test
    @DisplayName("문서 생성 후 수정 테스트")
    public void test1(){
        //given
        Notice n1 = new Notice();
        n1.setContent("write 1");
        n1.setMemberId("testId");
        nr.save(n1);
        Assertions.assertThat(n1.getContent()).isEqualTo("write 1");

        //when
        n1.setContent("write 2");

        //then
    }

    @Test
    @DisplayName("모든 문서 읽어오기")
    public void test2(){
        Member member = new Member("ktop1017", "jkc1073");
    }

}
