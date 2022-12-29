package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class MainController {

    MemberService memberService;
    NoticeService noticeService;
    CategoryService categoryService;

    @Autowired
    MainController(MemberService memberService, NoticeService noticeService, CategoryService categoryService){
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String rootController(
            Model model, HttpServletRequest req){

        // check session
        HttpSession session = req.getSession(false);

        // session not exist
        if (session == null) {
            model.addAttribute("member", null);
            return "index";
        }

        // session exist
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember != null){
            // login succeeded
            model.addAttribute("member", loginMember);
//            model.addAttribute("noticeList", noticeService.findMemberNoticeList(loginMember));
            model.addAttribute("categories", categoryService.getCategories(loginMember));
            model.addAttribute("groupedNoticeListMap", noticeService.getNoticeListGroupedByCategory(loginMember));
        } else{
            // redirection with login error
            Map<String,?> flashMap = RequestContextUtils.getInputFlashMap(req);

            if (flashMap!=null){
                try{
//                    Map<String, String> errorMap = (Map<String, String>) flashMap.get("errorMap");
//                    model.addAttribute("loginErrorMsg", errorMap.get("LoginError"));
                    String loginErrorMsg = (String) flashMap.get("loginErrorMsg");
                    String joinErrorMsg = (String) flashMap.get("joinErrorMsg");
                    String noTextErrorMsg = (String) flashMap.get("noTextErrorMsg");
                    model.addAttribute("loginErrorMsg", loginErrorMsg);
                    model.addAttribute("joinErrorMsg", joinErrorMsg);
                    model.addAttribute("noTextErrorMsg", noTextErrorMsg);
                }catch(NullPointerException e){
                    System.err.println("null pointer error : main controller");
                }
            }
        }

        return "index";
    }

    @GetMapping("info-setting")
    public String infoSetting(){
        return "infoSetting";
    }
}



