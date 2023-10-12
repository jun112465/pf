package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.PostDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.PostService;
import jun.studyHelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping(value="/")
    public String newRoot(
            Model model,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "categoryId", required = false) String categoryId
    ){

        // data to pass
        // 1. user
        // 2. posts
        // 3. categories


        List<PostDTO> postDTOs;

        if(sessionId == null || !loginService.isUserLoggedIn(sessionId)) {
            // login X
            model.addAttribute("user", null);
            postDTOs = postService.convertPostListToDTO(
                    postService.findPostList()
            );
        }else{
            // login O
            UserDTO userDTO = loginService.getUserDTO(sessionId);
            model.addAttribute("user", userDTO);
            model.addAttribute("categories", categoryService.getCategories(userDTO));


            if(userId == null)
                postDTOs = postService.convertPostListToDTO(
                        postService.findPostList()
                );
            else if(userId != null && categoryId == null)
                postDTOs = postService.convertPostListToDTO(
                        postService.findUserPostList(userDTO)
                );
            else
                postDTOs = postService.convertPostListToDTO(
                        postService.getPostsByCategory(
                                CategoryDTO.builder()
                                        .id(Long.parseLong(categoryId))
                                        .build()
                        )
                );
        }

        model.addAttribute("posts", postDTOs);

        return "main";
    }


    public String root(
            Model model,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId
    ){


        // Create Model
        List<Post> postList = postService.findPostList();
        List<PostDTO> postDTOList = postService.convertPostListToDTO(postList);
        model.addAttribute("posts", postDTOList);
        // login X Model
        if(sessionId == null || !loginService.isUserLoggedIn(sessionId)) {
            model.addAttribute("user", null);
        }
        // login O Model
        else {
            // postList
            UserDTO userDTO = loginService.getUserDTO(sessionId);
            List<Post> userPosts = postService.findUserPostList(userDTO);
            List<PostDTO> userPostDTOs = postService.convertPostListToDTO(userPosts);
            model.addAttribute("userPosts", userPostDTOs);

            UserDTO loginUser = loginService.getUserDTO(sessionId);
            model.addAttribute("user", loginUser);
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



