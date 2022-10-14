package jun.studyHelper.repository;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.repository.member.JpaMemberRepo;
import jun.studyHelper.repository.notice.JpaNoticeRepo;
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
    JpaNoticeRepo nr;
    @Autowired
    JpaMemberRepo mr;

    @Test
    @DisplayName("문서 생성하기")
    public void test0(){

        //given
        Notice n1 = new Notice();

        Member m = new Member();
        m.setUid("233");
        m.setPw("wfe");

        mr.saveAndFlush(m);

        //when
        m = mr.findByUid(m.getUid()).get(0);
        n1.setMember(m);
        nr.saveAndFlush(n1);

        System.out.println(nr.findAll());
        System.out.println(nr.findByMember(m));

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
