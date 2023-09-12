package jun.studyHelper.repositoryTest;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.user.UserRepository;
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
    UserRepository memberRepo;


    Category testCategory;
    User testUser;


    @BeforeEach
    @Test
    public void beforeEach(){
        testUser = User.builder()
                .uid("testId")
                .pw("testPw")
                .build();
        memberRepo.save(testUser);

        testCategory = Category.builder()
                .user(testUser)
                .name("testCategory")
                .build();
    }

    @Test
    @DisplayName("카테고리 추가")
    public void addCategoryTest(){
        categoryRepo.save(testCategory);
        Assertions.assertThat(categoryRepo.findAllByUserId(testUser.getId()).get(0)).isEqualTo(testCategory);
    }

    @Test
    @DisplayName("카테고리 삭제")
    public void deleteCategoryTest(){
        categoryRepo.save(testCategory);
        categoryRepo.delete(testCategory);
        Assertions.assertThat(categoryRepo.findAllByUserId(testUser.getId())).isEmpty();
    }

}
