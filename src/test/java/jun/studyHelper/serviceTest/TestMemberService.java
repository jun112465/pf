package jun.studyHelper.serviceTest;


import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.repository.member.JpaMemberRepo;
import jun.studyHelper.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class TestMemberService {


    @Autowired
    JpaMemberRepo mr;
    @Autowired
    MemberService ms;

    @Test
    @DisplayName("빈 폼 여부 확인")
    public void emptyFormSignUp(){
        // given
        Member member = new Member();
        member.setUid("");
        member.setPw("");

        // when & then
        Assertions.assertThat(ms.isMemberBlank(member)).isTrue();
    }


    @Test
    @DisplayName("로그인 테스트")
    public void loginTest(){

        // given
        Member member = new Member();
        member.setUid(String.valueOf(UUID.randomUUID()));
        member.setPw(String.valueOf(UUID.randomUUID()));
        ms.join(member);

        // when
        Member loginMember = ms.findMemberByUid(member).get();
        System.out.println(loginMember);

        // then
        Assertions.assertThat(ms.validateMemberInfo(loginMember)).isTrue();

    }

}
