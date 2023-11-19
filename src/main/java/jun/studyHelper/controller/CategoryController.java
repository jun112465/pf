package jun.studyHelper.controller;

import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {

    CategoryService categoryService;
    UserService userService;

    @Autowired
    public CategoryController(
            CategoryService categoryService,
            UserService userService){
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @GetMapping("/category")
    @ResponseBody
    public List<Category> get(@RequestParam(value = "userId")String userId){

        User user = userService.findUser(UserDto.builder().userId(userId).build()).get();
        UserDto userDTO = UserDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();

        return categoryService.getCategories(userDTO);
    }
}
