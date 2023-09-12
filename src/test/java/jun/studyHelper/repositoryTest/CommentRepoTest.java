package jun.studyHelper.repositoryTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.CommentRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.notice.NoticeRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//@Transactional
@SpringBootTest
public class CommentRepoTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    private User user;
    private Category category;
    private Notice notice;

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

        notice = Notice.builder()
                .user(user)
                .category(category)
                .build();
        notice = noticeRepository.save(notice);
    }

    @Test
    @DisplayName("일반 댓글 등록")
    public void add1(){

        //given
        Comment comment = Comment.builder()
                .user(user)
                .notice(notice)
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
                .notice(notice)
                .content("test comment")
                .parentComment(null)
                .children(new ArrayList<>())
                .build();

        Comment childComment = Comment.builder()
                .user(user)
                .notice(notice)
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
                .notice(notice)
                .content("test comment")
                .parentComment(null)
                .children(new ArrayList<>())
                .build();

        Comment childComment1 = Comment.builder()
                .user(user)
                .notice(notice)
                .content("test comment")
                .parentComment(parentComment)
                .children(null)
                .build();
        Comment childComment2 = Comment.builder()
                .user(user)
                .notice(notice)
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
        Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(3);
        Assertions.assertThat(commentRepository.findByParentComment(parentComment).size()).isEqualTo(2);
    }


}
