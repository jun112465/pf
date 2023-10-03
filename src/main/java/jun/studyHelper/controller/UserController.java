package jun.studyHelper.controller;


import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.dto.PostDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
public class UserController {

    public UserService userService;
    public LoginService loginService;
    public FileService fileService;
    public PostService postService;
    public CategoryService categoryService;



    @Autowired
    public UserController(
            UserService userService,
            LoginService loginService,
//            FileService fileService,
            PostService postService,
            CategoryService categoryService
    ) {
        this.userService = userService;
        this.loginService = loginService;
//        this.fileService = fileService;
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @PostMapping("/user/new")
    @ResponseBody
    public String create(@RequestBody UserDTO userDTO){

        if(userService.findMember(userDTO).isPresent())
            return "DUPLICATED UID";

        User user = userService.join(userDTO).orElse(null);
        // 초기 카테고리 & 노트 생성

        // add user
        System.out.println("멤버 등록 후 " + user.toString());
        System.out.println("userId : " + user.getId());
        System.out.println(user);


        // add first category
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .userId(user.getId())
                .name("Set Category Name")
                .build();
        Category category = categoryService.addCategory(categoryDTO).orElse(null);
        System.out.println("categoryId : " + category.getId());
        System.out.println(category);

        // add first note
        PostDTO postDTO = PostDTO.builder()
                .userId(user.getId())
                .categoryId(category.getId())
                .content("first note")
                .date(Post.getCurrentDate())
                .build();
        postService.add(postDTO);

        return "new user added";
    }


    @PostMapping("/user/login")
    @ResponseBody
    public String login(
            @RequestBody UserDTO userDTO,
            HttpServletResponse response
    ){

        System.out.println("MemberController login Func");
        System.out.println("request body");
        System.out.println(userDTO);

        if(userService.validateMemberInfo(userDTO)) {
            String sessionId = loginService.login(userDTO);

            // 쿠키 환경 설정 및 추가
            Cookie mySessionCookie = new Cookie(SessionConst.SESSION_ID, sessionId);
            mySessionCookie.setMaxAge(30 * 24 * 60 * 60 * 1000);
            mySessionCookie.setHttpOnly(true);
            mySessionCookie.setPath("/");
//            mySessionCookie.setPath("/");
            response.addCookie(mySessionCookie);

            //logging
            System.out.println("COOKIE name : " + mySessionCookie.getName());
            System.out.println("COOKIE value : " + mySessionCookie.getValue());
        }
        else{
//            Map<String, String> errorMap = new HashMap<>();
//            errorMap.put("LoginError", "No Member Found, Try Again");
//            String msg = "No Member Found, Try Again";
//            redirectAttributes.addFlashAttribute("loginErrorMsg", msg);
        }

        return "login";
    }

    @GetMapping ("/user/logout")
    public String logout(
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId,
            HttpServletResponse resp){

        // delete session in redis
        loginService.logout(sessionId);

        // session 쿠키 삭제
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);

        return "redirect:/";
    }


    @PostMapping("/user/update")
    public String updateProfile(
            @RequestParam("profile") MultipartFile profileImg,
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            HttpServletRequest req
    ){
        User user = (User)req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);


        if(!profileImg.isEmpty()){
            String PATH = "profiles/";
            String newFileName = PATH + user.getId();
            System.out.println("newFileName : " + newFileName);
            try {
                fileService.uploadToS3(profileImg, newFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(id);
        if(!id.isEmpty()){
            user.setUid(id);

        }

        System.out.println(pw);
        if(!pw.isEmpty()){
            user.setPw(pw);
        }

        return "redirect:/";
    }

}
