package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.notice.PostRepository;
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

    public Category findCategory(CategoryDTO categoryDTO){
        return categoryRepository.findById(categoryDTO.getId()).orElse(null);
    }

    /**
     * 성공하면 entity 반환
     * 실패 시 null 반환
     *
     * @param categoryDTO
     * @return
     */
    public Optional<Category> addCategory(CategoryDTO categoryDTO){
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

    public List<Category> getCategories(UserDTO userDTO){
        return categoryRepository.findAllByUserId(userDTO.getId());
    }


    public boolean validateCategory(CategoryDTO categoryDTO){
        List<Category> noticeCategories =categoryRepository.findAllByUserId(categoryDTO.getUserId());
        for(Category c : noticeCategories){
            if (c.getName().equals(categoryDTO.getName()))
                return false;
        }

        return true;
    }


    public void deleteCategory(CategoryDTO categoryDTO){
        Category category = categoryRepository.getById(categoryDTO.getId());
        postRepository.deleteAllByCategory(category);
        categoryRepository.deleteById(categoryDTO.getId());
//        noticeService.deleteNoticeListByCategory(categoryDTO.);
//        noticeService.deleteNoticeListByCategory(categoryDTO);
//        ncr.deleteById(id);
    }

    public Category findByUserAndName(CategoryDTO categoryDTO){
        return categoryRepository
                .findCategoryByUserIdAndName(categoryDTO.getUserId(), categoryDTO.getName())
                .orElse(null);
    }



}
