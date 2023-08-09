package jun.studyHelper.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import jun.studyHelper.controller.MemberController;
import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
//@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;
    @MockBean
    NoticeService noticeService;
    @MockBean
    CategoryService categoryService;
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

        given(memberService.findMember(any())).willReturn(Optional.empty());
        given(memberService.join(any())).willReturn(
                Member.builder()
                        .uid("testId")
                        .pw("testPw")
                        .build()
        );
        given(categoryService.addCategory(any())).willReturn(null);
        given(noticeService.add(any())).willReturn(null);

        String testUid = "testUid";
        String testPwd = "testPwd";

        MemberDTO memberDTO = MemberDTO.builder()
                .uid(testUid)
                .pwd(testPwd)
                .build();

        mockMvc.perform(post("/members/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isOk())
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
    
}
