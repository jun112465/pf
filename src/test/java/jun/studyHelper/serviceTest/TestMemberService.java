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

@SpringBootTest
@Transactional
public class TestMemberService {


    @Autowired
    JpaMemberRepo mr;
    @Autowired
    MemberService ms;

    @Test
    @DisplayName("빈 폼 전송 시 회원가입")
    public void emptyFormSignUp(){
        // given
        Member member = new Member();
        member.setUid("");
        member.setPw("");

        // when & then
        Assertions.assertThat(ms.isMemberBlank(member)).isTrue();
    }

}
