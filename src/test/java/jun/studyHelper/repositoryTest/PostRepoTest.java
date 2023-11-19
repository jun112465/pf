package jun.studyHelper.repositoryTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostRepoTest {
/*


    @Autowired
    PostRepository noticeRepo;
    @Autowired
    UserRepository memberRepo;
    @Autowired
    CategoryRepository categoryRepo;

    static User testUser;
    static Category testCategory;
    static Post testPost;

    @BeforeEach
    @Test
    public void beforeTest(){
        testUser = User.builder()
                .uid("testId")
                .pw("testPw")
                .build();
        memberRepo.save(testUser);

        testCategory = Category.builder()
                .user(testUser)
                .name("testCategory")
                .build();
        categoryRepo.save(testCategory);

        testPost = Post.builder()
                .user(testUser)
                .category(testCategory)
                .date(Post.getCurrentDate())
                .build();
    }

    @Test
    @DisplayName("노트 추가하기")
    public void noticeAddTest(){
        noticeRepo.save(testPost);
        Assertions.assertThat(noticeRepo.findAll().get(0)).isEqualTo(testPost);
        Assertions.assertThat(noticeRepo.findAll().get(0).getDate()).isEqualTo(Post.getCurrentDate());
    }

    @Test
    @DisplayName("노트 삭제하기")
    public void noticeDeleteTest(){
        noticeRepo.save(testPost);
        Assertions.assertThat(noticeRepo.findAll().get(0)).isEqualTo(testPost);

        noticeRepo.delete(testPost);
        Assertions.assertThat(noticeRepo.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("노트 수정 테스트")
    public void noticeUpdateTest(){
        System.out.println("------------------0-----------------");

        String testContent = "Test Content";
        System.out.println("------------------1-----------------");
        noticeRepo.save(testPost);


        System.out.println("------------------2-----------------");
        testPost = noticeRepo.findAll().get(0);
        testPost.setContent(testContent);

        System.out.println(noticeRepo.findById(testPost.getId()));
        System.out.println("------------------3-----------------");
        noticeRepo.save(testPost);

        Assertions.assertThat(noticeRepo.findAll().get(0).getContent()).isEqualTo(testContent);
    }

 */
}