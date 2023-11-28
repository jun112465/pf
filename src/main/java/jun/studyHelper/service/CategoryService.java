package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.post.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    CategoryRepository categoryRepository;
    UserRepository userRepository;
    PostRepository postRepository;
    UserService userService;

    PostService postService;



    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserService userService, PostService postService, UserRepository userRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Optional<Category> findCategory(CategoryDto categoryDTO){

        Optional<Category> category = categoryRepository.findById(categoryDTO.getId());
        if(category.isPresent()) return category;

        User user = userRepository.findById(categoryDTO.getUserId()).get();
        return categoryRepository.findCategoryByUserAndName(user, categoryDTO.getName());
    }

    /**
     * 성공하면 entity 반환
     * 실패 시 null 반환
     *
     * @param categoryDTO
     * @return
     */
    public Optional<Category> addCategory(CategoryDto categoryDTO){
        Category category = null;
        if(validateCategory(categoryDTO)){
            User user = userRepository.findById(categoryDTO.getUserId()).orElse(null);

            category = categoryRepository.save(Category.builder()
                    .name(categoryDTO.getName())
                    .user(user)
                    .build());
        }

        return Optional.ofNullable(category);
    }

    public List<Category> getCategories(UserDto userDTO){
        return categoryRepository.findAllByUser(userRepository.findById(userDTO.getUserId()).get());
    }


    public boolean validateCategory(CategoryDto categoryDTO){
        User user = userRepository.findById(categoryDTO.getUserId()).get();
        List<Category> noticeCategories =categoryRepository.findAllByUser(user);
        for(Category c : noticeCategories){
            if (c.getName().equals(categoryDTO.getName()))
                return false;
        }

        return true;
    }


    public void deleteCategory(CategoryDto categoryDTO){
        Category category = categoryRepository.getById(categoryDTO.getId());
        postRepository.deleteAllByCategory(category);
        categoryRepository.deleteById(categoryDTO.getId());
//        noticeService.deleteNoticeListByCategory(categoryDTO.);
//        noticeService.deleteNoticeListByCategory(categoryDTO);
//        ncr.deleteById(id);
    }

    public Category findByUserAndName(CategoryDto categoryDTO){
        User user = userRepository.findById(categoryDTO.getUserId()).get();
        return categoryRepository
                .findCategoryByUserAndName(user, categoryDTO.getName())
                .orElse(null);
    }



}
