package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.user.UserRepository;
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
    CategoryRepository repository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void delete(){

        User find = userRepository.findById("0bw66770gc").get();
        Category category = Category.builder()
                .user(find)
                .name("t1")
                .build();
        category = repository.save(category);
        repository.deleteById(category.getId());
    }
}
