package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.dto.PostDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.PostService;
import jun.studyHelper.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserRepository userRepository;



    PostDTO testPostDTO;
    UserDTO testUserDTO;
    CategoryDTO testCategoryDTO;

    User user;
    Category category;



    @BeforeEach
    public void beforeEach(){
        testUserDTO = UserDTO.builder()
                .uid("testId")
                .pwd("testPw")
                .build();
        user = userService.join(testUserDTO).orElse(null);

        testCategoryDTO = CategoryDTO.builder()
                .userId(user.getId())
                .name("testCategory")
                .build();
        category = categoryService.addCategory(testCategoryDTO).orElse(null);


    }

    @Test public void addPostTest(){
        //given
        testPostDTO = PostDTO.builder()
                .userId(user.getId())
                .categoryId(category.getId())
                .date(Post.getCurrentDate())
                .content("TEST NOTE")
                .build();

        //when
        Post post = postService.add(testPostDTO).orElse(null);

        //then
//        Assertions.assertThat(postService.findPostList().get(0)).isEqualTo(post);
        testUserDTO.setId(user.getId());
        System.out.println(postService.findUserPostList(testUserDTO));
    }


}