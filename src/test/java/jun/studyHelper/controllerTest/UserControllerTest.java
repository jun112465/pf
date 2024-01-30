package jun.studyHelper.controllerTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jun.studyHelper.controller.UserController;
import jun.studyHelper.model.dto.JwtToken;
import jun.studyHelper.model.dto.PasswordChangeRequest;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.dto.UserLoginRequestDto;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import javax.servlet.http.Cookie;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
//@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;

    @MockBean
    PostService postService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    LoginService loginService;
//    @BeforeEach
//    public void setup(){
//        mockMvc = MockMvcBuilders.standaloneSetup(MemberController.class).build();
//    }
    @Test
    @DisplayName("Hello Test")
    void Hello_Test() throws Exception {
        //given
        String hello = "hello";
        //when

        //then
//        mockMvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 GET 요청
//                //mvc.perform()의 결과를 검증
//                .andExpect(status().isOk()) //200 상태
//                .andExpect(content().string(hello)); //응답 본문의 내용을 검증
    }

    @Test
    @DisplayName("회원가입 정상 예제")
    void signUP() throws Exception {
//        given(userService.findMember(any())).willReturn(Optional.empty());
//        given(userService.join(any())).willReturn(
//                Optional.ofNullable(User.builder()
//                        .uid("testUid")
//                        .pw("testPw")
//                        .build())
//        );
//        given(categoryService.addCategory(any())).willReturn(
//                Optional.ofNullable(Category.builder().build())
//        );
//        given(postService.add(any())).willReturn(
//                Optional.ofNullable(Post.builder().build())
//        );
//
//
//        // given
//        String testUid = "testUid";
//        String testPwd = "testPwd";
//        UserDto userDTO = UserDto.builder()
//                .uid(testUid)
//                .pwd(testPwd)
//                .build();
//
//        //when
//        ResultActions actions = mockMvc.perform(post("/members/new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDTO)));
//
//        //then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(content().string("new member added"))
//                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 중복 uid")
    void signUpDuplicateUid() throws Exception{
        String json = "{\"uid\" : \"testId\", \"pwd\" : \"testPw\"}";
        mockMvc.perform(post("/members/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        json = "{\"uid\" : \"testId\", \"pwd\" : \"testPw\"}";
        mockMvc.perform(post("/members/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void givenValidCredentials_whenLogin_thenSetCookie() throws Exception {
        UserLoginRequestDto userLoginRequest = new UserLoginRequestDto("0bw66770gc", "wt0m6we738");

        // 로그인 요청
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 반환된 쿠키 확인
        MockHttpServletResponse response = result.getResponse();
        Cookie jwtCookie = response.getCookie("jwtToken");
        assertNotNull(jwtCookie);
        assertEquals("jwtToken", jwtCookie.getName());
        // 쿠키의 값이 비어있지 않은지 또는 null이 아닌지 확인하는 로직을 추가할 수 있습니다.
        assertNotNull(jwtCookie.getValue());
    }


    @Test
    @DisplayName("로그인 테스트")
    void loginTest() throws Exception {
        UserLoginRequestDto userLoginRequest = new UserLoginRequestDto("0bw66770gc", "wt0m6we738");



        mockMvc.perform(
                post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest))
        ).andDo(print());
    }


    @Test
    @DisplayName("비밀번호 변경 테스트")
//    @WithUserDetails("testUser")
    void testPasswordChange() throws Exception {
        PasswordChangeRequest pwChangeReq = PasswordChangeRequest.builder()
                .confirmPassword("wt0m6we738")
                .newPassword("testPw")
                .confirmPassword("testPw")
                .build();

        mockMvc.perform(
                post("/users/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(pwChangeReq))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    @DisplayName("로그인 테스트")
//    void loginTest() throws Exception {
//        given(userService.validateMemberInfo(any())).willReturn(true);
//
//        //given
//        // given
//        String testUid = "testUid";
//        String testPwd = "testPwd";
//        UserDto userDTO = UserDto.builder()
//                .uid(testUid)
//                .pwd(testPwd)
//                .build();
//
//        //when
//        ResultActions actions = mockMvc.perform(post("/members/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDTO)));
//
//        //then
//        actions.andExpect(status().is(302))
//                .andDo(print());
//
//
//
//    }


    
}
