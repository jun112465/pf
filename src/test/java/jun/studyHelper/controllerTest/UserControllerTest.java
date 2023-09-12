package jun.studyHelper.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import jun.studyHelper.controller.UserController;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.ResultActions;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    NoticeService noticeService;
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
        given(userService.findMember(any())).willReturn(Optional.empty());
        given(userService.join(any())).willReturn(
                Optional.ofNullable(User.builder()
                        .uid("testUid")
                        .pw("testPw")
                        .build())
        );
        given(categoryService.addCategory(any())).willReturn(
                Optional.ofNullable(Category.builder().build())
        );
        given(noticeService.add(any())).willReturn(
                Optional.ofNullable(Notice.builder().build())
        );


        // given
        String testUid = "testUid";
        String testPwd = "testPwd";
        UserDTO userDTO = UserDTO.builder()
                .uid(testUid)
                .pwd(testPwd)
                .build();

        //when
        ResultActions actions = mockMvc.perform(post("/members/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(content().string("new member added"))
                .andDo(print());
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
    @DisplayName("로그인 테스트")
    void loginTest() throws Exception {
        given(userService.validateMemberInfo(any())).willReturn(true);

        //given
        // given
        String testUid = "testUid";
        String testPwd = "testPwd";
        UserDTO userDTO = UserDTO.builder()
                .uid(testUid)
                .pwd(testPwd)
                .build();

        //when
        ResultActions actions = mockMvc.perform(post("/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        //then
        actions.andExpect(status().is(302))
                .andDo(print());



    }


    
}
