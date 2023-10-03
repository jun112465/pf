package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.PostService;
import jun.studyHelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    UserService userService;
    LoginService loginService;
    PostService postService;
    CategoryService categoryService;


    @Autowired
    MainController(UserService userService,
                   LoginService loginService,
                   PostService postService,
                   CategoryService categoryService){
        this.userService = userService;
        this.loginService = loginService;
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String root(
            Model model,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId
    ){

        if(sessionId == null || !loginService.isUserLoggedIn(sessionId)) {
            model.addAttribute("posts", postService.findPostList());
            model.addAttribute("user", null);
        }
        else {
            UserDTO loginUser = loginService.getUserDTO(sessionId);
            model.addAttribute("user", loginUser);
            model.addAttribute("posts", postService.findPostList());
//            model.addAttribute("posts", postService.findMemberNoticeList(loginUser));
            model.addAttribute("categories", categoryService.getCategories(loginUser));
            model.addAttribute("groupedNoticeListMap", postService.getNoticeListGroupedByCategory(loginUser));
        }

        return "main";
    }


    @GetMapping("sign-up")
    public String signUp(){
        return "signUpPage";
    }

    @GetMapping("info-setting")
    public String infoSetting(){
        return "infoSetting";
    }

}



