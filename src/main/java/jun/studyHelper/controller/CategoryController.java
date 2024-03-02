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
import java.util.Optional;

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
    public CategoryDto add(
            @RequestBody CategoryDto categoryDto,
            @AuthenticationPrincipal UserDetails userDetails){

        categoryDto.setUserId(userDetails.getUsername());

        if(categoryService.findCategory(categoryDto).isEmpty()){
            Optional<Category> category = categoryService.addCategory(categoryDto);
            categoryDto.setId(category.get().getId());
            return categoryDto;
        }else return null;

    }
    @PostMapping("/delete")
    @ResponseBody
    public String delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CategoryDto categoryDto
    ){
        categoryService.deleteCategory(categoryDto);
        return "information";
    }
    @PostMapping("/update")
    @ResponseBody
    public String update(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CategoryDto categoryDto
    ){
        categoryService.updateName(categoryDto);
        return "information";
    }

}
