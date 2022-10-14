package jun.studyHelper.serviceTest;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.repository.notice.JpaNoticeRepo;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@SpringBootTest
@Transactional
public class TestNoticeService {
    @Autowired
    NoticeService ns;
    @Autowired
    MemberService ms;
    @Autowired
    EntityManager em;
    @Autowired
    JpaNoticeRepo nr;
    Member member;

    @BeforeEach
    public void beforeEach(){
        // 멤버 등록
        member = new Member();
        member.setUid(String.valueOf(UUID.randomUUID()));
        member.setPw(String.valueOf(UUID.randomUUID()));
        ms.join(member);
    }

    @AfterEach
    public void afterEach(){
        // 멤버 삭제
        ms.deleteMember(member);
    }


    @Test
    @DisplayName("문서 생성 테스트")
    @Modifying(clearAutomatically = true)
    public void test0(){
        //given
        Notice n = new Notice();
        n.setMember(member);

        //when
        ns.add(n);

        //then
        Assertions.assertThat(ns.isTodayNoticeAdded(n)).isTrue();
    }

    @Test
    @DisplayName("문서 중복 생성 테스트")
    public void test1(){
        //given
        Notice n = new Notice();
        n.setMember(member);

        //when
        ns.add(n);

        //then
        Assertions.assertThat(ns.add(n)).isNull();
    }
}
