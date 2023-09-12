package jun.studyHelper.repositoryTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.repository.notice.NoticeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NoticeRepoTest {


    @Autowired
    NoticeRepository noticeRepo;
    @Autowired
    UserRepository memberRepo;
    @Autowired
    CategoryRepository categoryRepo;

    static User testUser;
    static Category testCategory;
    static Notice testNotice;

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

        testNotice = Notice.builder()
                .user(testUser)
                .category(testCategory)
                .date(Notice.getCurrentDate())
                .build();
    }

    @Test
    @DisplayName("노트 추가하기")
    public void noticeAddTest(){
        noticeRepo.save(testNotice);
        Assertions.assertThat(noticeRepo.findAll().get(0)).isEqualTo(testNotice);
        Assertions.assertThat(noticeRepo.findAll().get(0).getDate()).isEqualTo(Notice.getCurrentDate());
    }

    @Test
    @DisplayName("노트 삭제하기")
    public void noticeDeleteTest(){
        noticeRepo.save(testNotice);
        Assertions.assertThat(noticeRepo.findAll().get(0)).isEqualTo(testNotice);

        noticeRepo.delete(testNotice);
        Assertions.assertThat(noticeRepo.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("노트 수정 테스트")
    public void noticeUpdateTest(){
        System.out.println("------------------0-----------------");

        String testContent = "Test Content";
        System.out.println("------------------1-----------------");
        noticeRepo.save(testNotice);


        System.out.println("------------------2-----------------");
        testNotice = noticeRepo.findAll().get(0);
        testNotice.setContent(testContent);

        System.out.println(noticeRepo.findById(testNotice.getId()));
        System.out.println("------------------3-----------------");
        noticeRepo.save(testNotice);

        Assertions.assertThat(noticeRepo.findAll().get(0).getContent()).isEqualTo(testContent);
    }
}
