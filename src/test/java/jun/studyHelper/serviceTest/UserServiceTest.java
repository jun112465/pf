package jun.studyHelper.serviceTest;


import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class UserServiceTest {


    @Autowired
    UserRepository mr;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void signUpTest(){
        // given
        UserDto userDTO = UserDto.builder()
                .userId("testID")
                .password("testPW")
                .build();

        // when
        userService.join(userDTO);

        // then
//        Assertions.assertThat(userService.findUser(userDTO).get().getUid()).isEqualTo(userDTO.getUid());
    }

    @Test
    public void validateUserTest(){
        UserDto userDto = UserDto.builder()
                .userId("0bw66770gc")
                .password("wt0m6we738")
                .build();

        Optional<User> find = userRepository.findById(userDto.getUserId());
        Assertions.assertThat(userDto.getPassword().equals(find.get().getPassword()));
    }

    @Test
    @DisplayName("로그인 이후 jwt token 생성 테스트")
    public void jwtCreateTest(){
        userService.login("0bw66770gc", "wt0m6we738");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void memberDeleteTest(){
        // given
        UserDto userDTO = UserDto.builder()
                .userId("testID")
                .password("testPW")
                .build();
        userService.join(userDTO);

        // when
        userService.deleteUser(userDTO);

        // then
        System.out.println(userService.findUser(userDTO));
        Assertions.assertThat(userService.findUser(userDTO)).isNull();
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
