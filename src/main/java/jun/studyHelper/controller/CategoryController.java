package jun.studyHelper.controller;

import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
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


    @GetMapping("/")
    @ResponseBody
    public List<Category> get(@RequestParam(value = "userId")String userId){

        User user = userService.findUser(UserDto.builder().userId(userId).build()).get();
        UserDto userDTO = UserDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();

        return categoryService.getCategories(userDTO);
    }

    @PostMapping("/add")
    @ResponseBody
    public boolean add(
            @RequestBody CategoryDto categoryDto,
            @AuthenticationPrincipal UserDetails userDetails){

        categoryDto.setUserId(userDetails.getUsername());

        if(categoryService.findCategory(categoryDto).isEmpty()){
            categoryService.addCategory(categoryDto);
            return true;
        }else{
            return false;
        }
    }
}
