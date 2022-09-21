package jun.studyHelper.repository;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.repository.notice.NoticeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@Transactional
public class NoticeTest {

    @Autowired
    NoticeRepository nr;

    @Test
    @DisplayName("문서 생성하기")
    public void test0(){

        //given
        Notice n1 = new Notice();
        n1.setContent("write 1");
        n1.setMemberId("testId");

        //when
        nr.save(n1);

        //then
        n1 = nr.findById(n1.getId());
        System.out.println(n1);
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
        nr.update(n1);

        //then
        n1 = nr.findById(n1.getId());
        Assertions.assertThat(n1.getContent()).isEqualTo("write 2");
    }

    @Test
    @DisplayName("모든 문서 읽어오기")
    public void test2(){
        Member member = new Member("ktop1017", "jkc1073");
        System.out.println(nr.findByMemberId(member));
    }

}
