package jun.studyHelper.serviceTest;


import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.repository.member.MemberRepository;
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
public class MemberServiceTest {


    @Autowired
    MemberRepository mr;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void signUpTest(){
        // given
        MemberDTO memberDTO = MemberDTO.builder()
                .uid("testID")
                .password("testPW")
                .build();

        // when
        memberService.join(memberDTO);

        // then
        Assertions.assertThat(memberService.findMember(memberDTO).get().getUid()).isEqualTo("testID");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void memberDeleteTest(){
        // given
        MemberDTO memberDTO = MemberDTO.builder()
                .uid("testID")
                .password("testPW")
                .build();
        memberService.join(memberDTO);

        // when
        memberService.deleteMember(memberDTO);

        // then
        Assertions.assertThat(memberService.findMember(memberDTO)).isNull();
    }


//    @Test
//    @DisplayName("로그인 테스트")
//    public void loginTest(){
//
//        MemberDTO memberDTO = MemberDTO.builder()
//                .uid("testID")
//                .password("testPW")
//                .build();
//        memberService.join(memberDTO);
//
//        memberService.
//
//
////        // given
////        Member signUpMember = new Member();
////        signUpMember.setUid(String.valueOf(UUID.randomUUID()));
////        signUpMember.setPw(String.valueOf(UUID.randomUUID()));
//////        ms.join(signUpMember);
////
////        // when
////        Member loginMember = new Member();
////        loginMember.setUid(signUpMember.getUid());
////        loginMember.setPw(signUpMember.getPw());
////
////        // then
////        Assertions.assertThat(ms.validateMemberInfo(loginMember)).isTrue();
//
//    }
}
