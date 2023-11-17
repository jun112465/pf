package jun.studyHelper.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jun.studyHelper.controller.PostController;
import jun.studyHelper.model.dto.PostDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.PostService;
import jun.studyHelper.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @MockBean
    PostService postService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    LoginService loginService;

    @Test
    @DisplayName("add post")
    void test1() throws Exception {

        given(postService.add(any())).willReturn(
                Optional.ofNullable(Post.builder().build())
        );

        // session cookie
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "user123");

        //given
        PostDTO postDTO = PostDTO.builder()
                .categoryId(1)
                .userId(1)
                .build();

        System.out.println(objectMapper.writeValueAsString(postDTO));

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/post/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO))
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/?userId=1&categoryId=1)"));
    }

    @Test
    @DisplayName("get N posts")
    void test2() {
        given(postService.add(any())).willReturn(
                Optional.ofNullable(Post.builder().build())
        );
        given(postService.findUserPostList(any())).willReturn(
                List.of(Post.builder().build())
        );

        //when & then
//        mockMvc.perform(MockMvcRequestBuilders.post("/post/")
//                        .contentType(MediaType.APPLICATION_JSON));
//                        .content(objectMapper.writeValueAsString(postDTO))
//                        .session(session))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/?userId=1&categoryId=1)"));
    }
}
