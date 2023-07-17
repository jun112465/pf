package jun.studyHelper.repositoryTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRepoTest {

    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    MemberRepository memberRepo;


    Category testCategory;
    Member testMember;


    @BeforeEach
    @Test
    public void beforeEach(){
        testMember = Member.builder()
                .uid("testId")
                .pw("testPw")
                .build();
        memberRepo.save(testMember);

        testCategory = Category.builder()
                .member(testMember)
                .name("testCategory")
                .build();
    }

    @Test
    @DisplayName("카테고리 추가")
    public void addCategoryTest(){
        categoryRepo.save(testCategory);
        Assertions.assertThat(categoryRepo.findAllByMemberId(testMember.getId()).get(0)).isEqualTo(testCategory);
    }

    @Test
    @DisplayName("카테고리 삭제")
    public void deleteCategoryTest(){
        categoryRepo.save(testCategory);
        categoryRepo.delete(testCategory);
        Assertions.assertThat(categoryRepo.findAllByMemberId(testMember.getId())).isEmpty();
    }

}
