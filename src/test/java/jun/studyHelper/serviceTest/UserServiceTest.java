package jun.studyHelper.serviceTest;


import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {


    @Autowired
    UserRepository mr;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void signUpTest(){
        // given
        UserDTO userDTO = UserDTO.builder()
                .uid("testID")
                .pwd("testPW")
                .build();

        // when
        userService.join(userDTO);

        // then
        Assertions.assertThat(userService.findMember(userDTO).get().getUid()).isEqualTo(userDTO.getUid());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void memberDeleteTest(){
        // given
        UserDTO userDTO = UserDTO.builder()
                .uid("testID")
                .pwd("testPW")
                .build();
        userService.join(userDTO);

        // when
        userService.deleteMember(userDTO);

        // then
        Assertions.assertThat(userService.findMember(userDTO)).isNull();
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
