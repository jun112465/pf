package jun.studyHelper.repositoryTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.CommentRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.notice.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@Transactional
@SpringBootTest
@Transactional
public class CommentRepoTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;

    private User user;
    private Category category;
    private Post post;

    @BeforeEach
    public void before(){
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
    @DisplayName("일반 댓글 등록")
    public void add1(){

        //given
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .build();
        //when
        comment = commentRepository.save(comment);

        //then
        System.out.println(comment);

    }

    @Test
    @DisplayName("대댓글 등록")
    public void add2(){

        //given
        Comment parentComment = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(null)
                .children(new ArrayList<>())
                .build();

        Comment childComment = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(parentComment)
                .children(null)
                .build();


        //when
        parentComment = commentRepository.save(parentComment);
        childComment = commentRepository.save(childComment);

        //then
        System.out.println(parentComment);
        System.out.println(childComment);
    }

    @Test
    @DisplayName("대댓글 등록 후 조회")
    public void addAndRead(){
        //given
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

        //when
        List<Comment> children = new ArrayList<>();
        children.add(childComment1);
        children.add(childComment2);
        parentComment.setChildren(children);
        parentComment = commentRepository.save(parentComment);

        //then
        Assertions.assertThat(commentRepository.findByPostIdAndParentCommentIdIsNull(post.getId()).size()).isEqualTo(1);
        Assertions.assertThat(commentRepository.findByPostAndParentCommentIsNull(post).size()).isEqualTo(1);
        Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(3);
        Assertions.assertThat(commentRepository.findByParentComment(parentComment).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("toString 에러 테스트 : 순환참조")
    public void toStringTest(){
        //given
        Comment parentComment = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(null)
                .children(new ArrayList<>())
                .build();

        Comment childComment = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(parentComment)
                .children(null)
                .build();


        //when
        parentComment.getChildren().add(childComment);

        //then
        System.out.println(parentComment);
    }

    @Test
    @DisplayName("게시글의 댓글 불러오기")
    void findByPostId(){

        //given
        Comment c1 = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(null)
                .children(null)
                .build();
        Comment c2 = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(null)
                .children(null)
                .build();
        Comment c3 = Comment.builder()
                .user(user)
                .post(post)
                .content("test comment")
                .parentComment(null)
                .children(null)
                .build();
        //when
        commentRepository.save(c1);
        commentRepository.save(c2);
        commentRepository.save(c3);
        List<Comment> commentList = commentRepository.findByPostId(post.getId());

        //then
        Assertions.assertThat(commentRepository.findByPostId(post.getId()).size()).isEqualTo(3);
        System.out.println(commentList);
    }


}
