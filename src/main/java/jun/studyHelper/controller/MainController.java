package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.entity.Member;
import jun.studyHelper.service.GroupService;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeCategoryService;
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
    GroupService groupService;
    NoticeCategoryService noticeCategoryService;

    @Autowired
    MainController(MemberService memberService, NoticeService noticeService, GroupService groupService, NoticeCategoryService noticeCategoryService){
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.groupService = groupService;
        this.noticeCategoryService = noticeCategoryService;
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
            model.addAttribute("categories", noticeCategoryService.getCategories(loginMember.getId()));
            model.addAttribute("groupedNoticeListMap", noticeService.getGroupedNoticeList(loginMember));
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
}



