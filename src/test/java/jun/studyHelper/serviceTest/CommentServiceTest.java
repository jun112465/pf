package jun.studyHelper.serviceTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.CommentRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.post.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    /*
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private Category category;
    private Post post;

    @BeforeEach
    public void beforeEach(){
        user = User.builder()
                .uid("testId")
                .pw("testPw")
                .build();
        user = userRepository.save(user);

        category = Category.builder()
                .name("testCategory")
                .user(user)
                .build();
        category = categoryRepository.save(category);

        post = Post.builder()
                .user(user)
                .category(category)
                .build();
        post = postRepository.save(post);
    }



    @Test
    @DisplayName("한 노트의 모든 댓글 계층형으로 가져오기")
    void test1(){

        // given
        // 댓글 등록
        Comment parentComment = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(null)
                .children(new ArrayList<>())
                .build();

        Comment childComment1 = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(parentComment)
                .children(null)
                .build();
        Comment childComment2 = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(parentComment)
                .children(null)
                .build();

        // cascade type = persist 이기 때문에 자식 댓글은 자동으로 저장된다
        commentRepository.save(parentComment);


        // when
        // 댓글을 가져오는데 부모의 댓글의 리스트를 얻는다


        // then
        // 이떄 각 부모 댓글의 아이들도 자동으로 있어야 한다.
        // 댓글 + 대댓글의 개수가 전체 댓글의 개수와 일치해야 한다.
    }



     */
}
