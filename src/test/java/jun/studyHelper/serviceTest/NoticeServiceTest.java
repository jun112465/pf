package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.repository.member.MemberRepository;
import jun.studyHelper.repository.notice.NoticeRepository;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.MemberService;
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
    MemberService memberService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    MemberRepository memberRepository;



    NoticeDTO testNoticeDTO;
    MemberDTO testMemberDTO;
    CategoryDTO testCategoryDTO;

    Member member;
    Category category;



    @BeforeEach
    public void beforeEach(){
        testMemberDTO = MemberDTO.builder()
                .uid("testId")
                .pwd("testPw")
                .build();
        member = memberService.join(testMemberDTO).orElse(null);

        testCategoryDTO = CategoryDTO.builder()
                .memberId(member.getId())
                .name("testCategory")
                .build();
        category = categoryService.addCategory(testCategoryDTO).orElse(null);


    }

    @Test public void addNoticeTest(){
        //given
        testNoticeDTO = NoticeDTO.builder()
                .memberId(member.getId())
                .categoryId(category.getId())
                .date(Notice.getCurrentDate())
                .content("TEST NOTE")
                .build();

        //when
        Notice notice = noticeService.add(testNoticeDTO);

        //then
        Assertions.assertThat(noticeService.findNoticeList().get(0)).isEqualTo(notice);
        System.out.println(noticeService.findMemberNoticeList(member));
    }


}