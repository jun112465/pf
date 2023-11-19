package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.PostDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.PostService;
import jun.studyHelper.service.UserService;
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



    PostDto testPostDto;
    UserDto testUserDto;
    CategoryDto testCategoryDto;

    User user;
    Category category;



    @BeforeEach
    public void beforeEach(){
        testUserDto = UserDto.builder()
                .userId("testId")
                .password("testPw")
                .build();
        user = userService.join(testUserDto).orElse(null);

        testCategoryDto = CategoryDto.builder()
                .userId(user.getUserId())
                .name("testCategory")
                .build();
        category = categoryService.addCategory(testCategoryDto).orElse(null);


    }

    @Test public void addPostTest(){
        //given
        testPostDto = PostDto.builder()
                .userId(user.getUserId())
                .categoryId(category.getId())
                .date(Post.getCurrentDate())
                .content("TEST NOTE")
                .build();

        //when
        Post post = postService.add(testPostDto).orElse(null);

        //then
//        Assertions.assertThat(postService.findPostList().get(0)).isEqualTo(post);
        testUserDto.setUserId(user.getUserId());
        System.out.println(postService.findUserPostList(testUserDto));
    }


}