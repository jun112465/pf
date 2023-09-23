package jun.studyHelper.controller;


import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
public class UserController {

    public UserService userService;
    public LoginService loginService;
    public FileService fileService;
    public NoticeService noticeService;
    public CategoryService categoryService;



    @Autowired
    public UserController(
            UserService userService,
            LoginService loginService,
//            FileService fileService,
            NoticeService noticeService,
            CategoryService categoryService
    ) {
        this.userService = userService;
        this.loginService = loginService;
//        this.fileService = fileService;
        this.noticeService = noticeService;
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
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .userId(user.getId())
                .categoryId(category.getId())
                .content("first note")
                .date(Notice.getCurrentDate())
                .build();
        noticeService.add(noticeDTO);

        return "new user added";
    }


    @PostMapping("/user/login")
    @ResponseBody
    public String login(
            @RequestBody UserDTO userDTO,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ){

        System.out.println("MemberController login Func");
        System.out.println("request body");
        System.out.println(userDTO);

        if(userService.validateMemberInfo(userDTO)) {
            String sessionId = loginService.login(userDTO);

            // 쿠키 환경 설정 및 추가
            Cookie mySessionCookie = new Cookie(SessionConst.SESSION_ID, sessionId);
            mySessionCookie.setMaxAge(30 * 24 * 60 * 60 * 1000);
            mySessionCookie.setPath("/");
            response.addCookie(mySessionCookie);

            //logging
            System.out.println("COOKIE name : " + mySessionCookie.getName());
            System.out.println("COOKIE value : " + mySessionCookie.getValue());
        }
        else{
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("LoginError", "No Member Found, Try Again");
            String msg = "No Member Found, Try Again";
            redirectAttributes.addFlashAttribute("loginErrorMsg", msg);
        }

        return "login";
    }

//    @PostMapping("/user/login")
    public String SessionLogin(
            UserDTO userDTO,
            HttpServletRequest req,
            RedirectAttributes redirect){

        System.out.println("SessionLogin");
        System.out.println(userDTO);

        System.out.println("조건문 직전");
        if(userService.validateMemberInfo(userDTO)) {
            // 사용자의 데이터를 찾은 경우
            System.out.println("조건문 통과");
            System.out.println(userService.findMember(userDTO));
            User loginUser = userService.findMember(userDTO).orElse(null);
            System.out.println("founded user");
            System.out.println(loginUser);

            HttpSession session = req.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER + "", loginUser);

            // 세션 유지 시간을 최대 하루로 잡음
            session.setMaxInactiveInterval(24*60*60);
        }else{
            // 사용자의 데이터를 찾지 못한 경우
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("LoginError", "No Member Found, Try Again");

            String msg = "No Member Found, Try Again";

            // addFlashAttribute 을 통해 세션이 생성된다.
            redirect.addFlashAttribute("loginErrorMsg", msg);
        }

        return "redirect:/";
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
