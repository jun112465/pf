package jun.studyHelper.controller;


import jun.studyHelper.model.dto.*;
import jun.studyHelper.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;


@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
@RequiredArgsConstructor
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserService userService;
    private final LoginService loginService;
//   rivateic FileService fileService;
    private final PostService postService;
    private final CategoryService categoryService;


    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        String memberId = userLoginRequestDto.getUserId();
        String password = userLoginRequestDto.getPassword();

        JwtToken jwtToken = userService.login(memberId, password);

        // 쿠키 환경 설정 및 추가
        Cookie mySessionCookie = new Cookie("accessToken", jwtToken.getAccessToken());
        mySessionCookie.setMaxAge(30 * 24 * 60 * 60 * 1000);
        mySessionCookie.setHttpOnly(true);
        mySessionCookie.setPath("/");
        response.addCookie(mySessionCookie);

        return "main";
    }

    @GetMapping("/get-current-member")
    @ResponseBody
    public String getCurrentMember(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/test")
    @ResponseBody
    public String test(){
        return "success";
    }

//    @PostMapping("/user/new")
//    @ResponseBody
//    public String create(@RequestBody UserDto userDTO){
//
//        if(userService.findMember(userDTO).isPresent())
//            return "DUPLICATED UID";
//
//        User user = userService.join(userDTO).orElse(null);
//        // 초기 카테고리 & 노트 생성
//
//        // add user
//        System.out.println("멤버 등록 후 " + user.toString());
//        System.out.println("userId : " + user.getId());
//        System.out.println(user);
//
//
//        // add first category
//        CategoryDto categoryDTO = CategoryDto.builder()
//                .userId(user.getId())
//                .name("Set Category Name")
//                .build();
//        Category category = categoryService.addCategory(categoryDTO).orElse(null);
//        System.out.println("categoryId : " + category.getId());
//        System.out.println(category);
//
//        // add first note
//        PostDto postDTO = PostDto.builder()
//                .userId(user.getId())
//                .categoryId(category.getId())
//                .content("first note")
//                .date(Post.getCurrentDate())
//                .build();
//        postService.add(postDTO);
//
//        return "new user added";
//    }
//
//
//    @PostMapping("/user/login")
//    @ResponseBody
//    public String login(
//            @RequestBody UserDto userDTO,
//            HttpSession session,
//            HttpServletResponse response
//    ){
//
//        System.out.println("MemberController login Func");
//        System.out.println("request body");
//        System.out.println(userDTO);
//
//        if(userService.validateMemberInfo(userDTO)) {
//            String sessionId = loginService.login(userDTO);
//
//            // 쿠키 환경 설정 및 추가
//            Cookie mySessionCookie = new Cookie(SessionConst.SESSION_ID, sessionId);
//            mySessionCookie.setMaxAge(30 * 24 * 60 * 60 * 1000);
//            mySessionCookie.setHttpOnly(true);
//            mySessionCookie.setPath("/");
////            mySessionCookie.setPath("/");
//            response.addCookie(mySessionCookie);
//
//            //logging
//            System.out.println("COOKIE name : " + mySessionCookie.getName());
//            System.out.println("COOKIE value : " + mySessionCookie.getValue());
//        }
//        else{
////            Map<String, String> errorMap = new HashMap<>();
////            errorMap.put("LoginError", "No Member Found, Try Again");
////            String msg = "No Member Found, Try Again";
////            redirectAttributes.addFlashAttribute("loginErrorMsg", msg);
//        }
//
//        return "login";
//    }
//
//    @GetMapping ("/user/logout")
//    public String logout(
//            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId,
//            HttpServletResponse resp){
//
//        // delete session in redis
//        loginService.logout(sessionId);
//
//        // session 쿠키 삭제
//        Cookie cookie = new Cookie("SESSIONID", null);
//        cookie.setMaxAge(0);
//        resp.addCookie(cookie);
//
//        // jsession 쿠키 삭제
//        cookie = new Cookie("JSESSIONID", null);
//        cookie.setMaxAge(-1);
//        resp.addCookie(cookie);
//
//
//        return "redirect:/";
//    }
//
//
//    @PostMapping("/user/update")
//    public String updateProfile(
//            @RequestParam("profile") MultipartFile profileImg,
//            @RequestParam("id") String id,
//            @RequestParam("pw") String pw,
//            HttpServletRequest req
//    ){
//        User user = (User)req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
//
//
//        if(!profileImg.isEmpty()){
//            String PATH = "profiles/";
//            String newFileName = PATH + user.getId();
//            System.out.println("newFileName : " + newFileName);
//            try {
//                fileService.uploadToS3(profileImg, newFileName);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        System.out.println(id);
//        if(!id.isEmpty()){
//            user.setUid(id);
//
//        }
//
//        System.out.println(pw);
//        if(!pw.isEmpty()){
//            user.setPw(pw);
//        }
//
//        return "redirect:/";
//    }

}
