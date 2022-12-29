package jun.studyHelper.serviceTest;


import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.repository.member.JpaMemberRepo;
import jun.studyHelper.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
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
        Member signUpMember = new Member();
        signUpMember.setUid(String.valueOf(UUID.randomUUID()));
        signUpMember.setPw(String.valueOf(UUID.randomUUID()));
        ms.join(signUpMember);

        // when
        Member loginMember = new Member();
        loginMember.setUid(signUpMember.getUid());
        loginMember.setPw(signUpMember.getPw());

        // then
        Assertions.assertThat(ms.validateMemberInfo(loginMember)).isTrue();

    }
}
