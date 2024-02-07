package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.PostDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.PostService;
import jun.studyHelper.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
public class MainController {

    UserService userService;
    PostService postService;
    CategoryService categoryService;


    @Autowired
    MainController(UserService userService,
                   LoginService loginService,
                   PostService postService,
                   CategoryService categoryService){
        this.userService = userService;
        this.postService = postService;
        this.categoryService = categoryService;
    }


    @GetMapping(value="/")
    public String newRoot(
            Model model,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @AuthenticationPrincipal UserDetails userDetails
    ){

        if(userDetails != null) {
            UserDto userDto = UserDto.builder()
                    .userId(userDetails.getUsername())
                    .build();

            User user = userService.findUser(userDto).get();

            model.addAttribute("user", user);
            log.info("User Logged In");

            model.addAttribute("categories", categoryService.getCategories(userDto));
        }
        else {
            model.addAttribute("user", null);
            log.info("No User Logged In");
        }

        if(!model.containsAttribute("posts")) {
            log.info("No posts Model");
            // 공통 모델 (게시글)
            List<PostDto> postDtoList = postService.getPostPage(1);
            model.addAttribute("posts", postDtoList);

            // page range
            model.addAttribute("pageInfo", postService.getPageRange(1));
            model.addAttribute("totalPage", postService.getTotalPage());
            model.addAttribute("pageNo", 1);
            model.addAttribute("categoryId", null);
        }

        if(model.containsAttribute("categoryId"))
            log.info("MODEL, categoryId : " + model.getAttribute("categoryId"));
        // data to pass
        // 1. user
        // 2. posts
        // 3. categories



//        if(sessionId == null || !loginService.isUserLoggedIn(sessionId)) {
//            // login X
//            model.addAttribute("user", null);
//            postDtos = postService.convertPostListToDTO(
//                    postService.findPostList()
//            );
//        }else{
//            // login O
//            UserDto userDTO = loginService.getUserDTO(sessionId);
//            model.addAttribute("user", userDTO);
//            model.addAttribute("categories", categoryService.getCategories(userDTO));
//
//
//            if(userId == null)
//                postDtos = postService.convertPostListToDTO(
//                        postService.findPostList()
//                );
//            else if(userId != null && categoryId == null)
//                postDtos = postService.convertPostListToDTO(
//                        postService.findUserPostList(userDTO)
//                );
//            else
//                postDtos = postService.convertPostListToDTO(
//                        postService.getPostsByCategory(
//                                CategoryDto.builder()
//                                        .id(Long.parseLong(categoryId))
//                                        .build()
//                        )
//                );
//        }
//
//        model.addAttribute("posts", postDtos);
//
//        return "main";
        if(model.containsAttribute("exampleAttribute"))
            log.info("test" + model.getAttribute("exampleAttribute"));
        return "main";
    }


    public String root(
            Model model,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId
    ){


        // Create Model
        List<Post> postList = postService.findPostList();
        List<PostDto> postDtoList = postService.convertPostListToDTO(postList);
        model.addAttribute("posts", postDtoList);
        // login X Model
//        if(sessionId == null || !loginService.isUserLoggedIn(sessionId)) {
//            model.addAttribute("user", null);
//        }
//        // login O Model
//        else {
//            // postList
//            UserDto userDTO = loginService.getUserDTO(sessionId);
//            List<Post> userPosts = postService.findUserPostList(userDTO);
//            List<PostDto> userPostDtos = postService.convertPostListToDTO(userPosts);
//            model.addAttribute("userPosts", userPostDtos);
//
//            UserDto loginUser = loginService.getUserDTO(sessionId);
//            model.addAttribute("user", loginUser);
//            model.addAttribute("categories", categoryService.getCategories(loginUser));
//            model.addAttribute("groupedNoticeListMap", postService.getNoticeListGroupedByCategory(loginUser));
//        }

        return "main";
    }


    @GetMapping("sign-up")
    public String signUp(){
        return "signUpPage";
    }

    @GetMapping("information")
    public String infoSetting(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if(userDetails != null) {
            UserDto userDto = UserDto.builder()
                    .userId(userDetails.getUsername())
                    .build();

            User user = userService.findUser(userDto).get();

            model.addAttribute("user", user);
            log.info("User Logged In");

            model.addAttribute("categories", categoryService.getCategories(userDto));
        }
        else {
            model.addAttribute("user", null);
            log.info("No User Logged In");
        }
        return "information";
    }

}



