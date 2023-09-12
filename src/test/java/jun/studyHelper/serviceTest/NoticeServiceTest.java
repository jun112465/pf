package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.NoticeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserRepository userRepository;



    NoticeDTO testNoticeDTO;
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

    @Test public void addNoticeTest(){
        //given
        testNoticeDTO = NoticeDTO.builder()
                .userId(user.getId())
                .categoryId(category.getId())
                .date(Notice.getCurrentDate())
                .content("TEST NOTE")
                .build();

        //when
        Notice notice = noticeService.add(testNoticeDTO).orElse(null);

        //then
        Assertions.assertThat(noticeService.findNoticeList().get(0)).isEqualTo(notice);
//        System.out.println(noticeService.findMemberNoticeList(user));
    }


}