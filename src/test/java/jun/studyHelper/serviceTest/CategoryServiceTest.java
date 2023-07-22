package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;
    @Autowired
    MemberService memberService;

    CategoryDTO categoryDTO;
    Member member;

    @BeforeEach
    public void beforeEach(){

        member = memberService.join(MemberDTO.builder()
                .uid("testId")
                .pwd("testPw")
                .build());

        categoryDTO = CategoryDTO.builder()
                .name("testCategory")
                .memberId(member.getId())
                .build();
    }

    @Test
    public void createCategoryTest(){
        Category category = categoryService.addCategory(categoryDTO);
        Assertions.assertThat(category.getId()).isEqualTo(1);

        category = categoryService.findByMemberAndName(categoryDTO);
        Assertions.assertThat(category.getId()).isEqualTo(1);
    }

    @Test
    public void deleteCategoryTest(){

        //given
        Category category = categoryService.addCategory(categoryDTO);
        categoryDTO.setId(category.getId());

        //when
        categoryService.deleteCategory(categoryDTO);

        //then
        Assertions.assertThat(categoryService.getCategories(MemberDTO.builder()
                .id(member.getId()).build())).isEmpty();
    }




}
