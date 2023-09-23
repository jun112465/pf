package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    UserService userService;
    LoginService loginService;
    NoticeService noticeService;
    CategoryService categoryService;


    @Autowired
    MainController(UserService userService,
                   LoginService loginService,
                   NoticeService noticeService,
                   CategoryService categoryService){
        this.userService = userService;
        this.loginService = loginService;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String root(
            Model model,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId
    ){
        if(sessionId == null || !loginService.isUserLoggedIn(sessionId))
            model.addAttribute("user", null);
        else {
            UserDTO loginUser = loginService.getUserDTO(sessionId);
            model.addAttribute("user", loginUser);
            model.addAttribute("noticeList", noticeService.findMemberNoticeList(loginUser));
            model.addAttribute("categories", categoryService.getCategories(loginUser));
            model.addAttribute("groupedNoticeListMap", noticeService.getNoticeListGroupedByCategory(loginUser));
        }

        return "index";
    }



//    @GetMapping("/")
//    public String rootController(
//            Model model, HttpServletRequest req){
//
//        // check session
//        HttpSession session = req.getSession(false);
//
//        // session not exist
//        if (session == null) {
//            model.addAttribute("member", null);
//            return "index";
//        }
//
//        // session exist
//        System.out.println("Session exist");
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        if (loginMember != null){
//            System.out.println();
//            System.out.println(loginMember.getId());
//
//            MemberDTO loginDTO = MemberDTO.builder()
//                    .id(loginMember.getId())
//                    .uid(loginMember.getUid())
//                    .pwd(loginMember.getPw())
//                    .build();
//
//            System.out.println();
//            System.out.println(categoryService.getCategories(loginDTO));
//            // login succeeded
//            model.addAttribute("member", loginMember);
////            model.addAttribute("noticeList", noticeService.findMemberNoticeList(loginMember));
//            model.addAttribute("categories", categoryService.getCategories(loginDTO));
//            model.addAttribute("groupedNoticeListMap", noticeService.getNoticeListGroupedByCategory(loginDTO));
//        }
//
//        return "index";
//    }

    @GetMapping("sign-up")
    public String signUp(){
        return "signUpPage";
    }

    @GetMapping("info-setting")
    public String infoSetting(){
        return "infoSetting";
    }

}



