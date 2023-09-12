package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    CategoryDTO categoryDTO;
    User user;

    @BeforeEach
    public void beforeEach(){

        user = userService.join(UserDTO.builder()
                .uid("testId")
                .pwd("testPw")
                .build())
                .orElse(null);

        categoryDTO = CategoryDTO.builder()
                .name("testCategory")
                .userId(user.getId())
                .build();
    }

    @Test
    public void createCategoryTest(){
        Category category = categoryService.addCategory(categoryDTO).orElse(null);
        Assertions.assertThat(category.getId()).isEqualTo(1);

        category = categoryService.findByUserAndName(categoryDTO);
        List<Category> list = categoryService.getCategories(UserDTO.builder()
                .id(user.getId())
                .uid(user.getUid())
                .pwd(user.getPw())
                .build());
        System.out.println(list);
        Assertions.assertThat(category.getId()).isEqualTo(1);
    }

    @Test
    public void deleteCategoryTest(){

        //given
        Category category = categoryService.addCategory(categoryDTO).orElse(null);
        categoryDTO.setId(category.getId());

        //when
        categoryService.deleteCategory(categoryDTO);

        //then
        Assertions.assertThat(categoryService.getCategories(UserDTO.builder()
                .id(user.getId()).build())).isEmpty();
    }




}
