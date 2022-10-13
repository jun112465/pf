package jun.studyHelper.serviceTest;


import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.repository.member.MemberRepository;
import jun.studyHelper.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestMember {


    @Autowired
    MemberRepository mr;
    @Autowired
    MemberService ms;

    @Test
    @DisplayName("회원가입")
    public void sign_up(){
        //given
        Member member = new Member("testId", "testPw");

        //when
        mr.save(member);

        //then
        Assertions.assertThat(mr.findById(member.getId()).orElse(null)).isEqualTo(member);
    }

    @Test
    @DisplayName("로그인 테스트")
    public void sign_in(){
        // given
        Member member = new Member("ktop1017", "jkc1073");

        // when,then
        member = ms.validateMemberInfo(member);

        // then
        Assertions.assertThat(member).isNotNull();
    }
}
