package jun.studyHelper.serviceTest;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestNotice {
    @Autowired
    NoticeService ns;
    @Autowired
    MemberService ms;

    @Test
    @DisplayName("문서 생성 테스트")
    @Modifying(clearAutomatically = true)
    public void test0(){
        //given
        Member m = new Member("testId","testPw");
        Notice n = new Notice();

        n.setDate(n.getCurrentDate());
        n.setMemberId(m.getId());
        n.setCategory("testCategory");

        // when
        ms.join(m);

        // then
        Assertions.assertThat(ns.add(n, m)).isNotNull();
    }

    @Test
    @DisplayName("문서 생성 중복 테스트")
    public void test1(){
        //given
        Member m = new Member("testId","testPw");
        Notice n = new Notice();

        n.setDate(n.getCurrentDate());
        n.setMemberId(m.getId());
        n.setCategory("testCategory");

        Notice n2 = new Notice();
        n2.setDate(n.getCurrentDate());
        n2.setMemberId(m.getId());
        n2.setCategory("testCategory");

        // when
        ms.join(m);
        ns.add(n, m);

        // then
        Assertions.assertThat(ns.add(n2,m)).isNull();
    }



}
