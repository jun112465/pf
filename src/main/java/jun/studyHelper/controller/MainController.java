package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.service.GroupService;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    MemberService memberService;
    NoticeService noticeService;
    GroupService groupService;

    @Autowired
    MainController(MemberService memberService, NoticeService noticeService, GroupService groupService){
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.groupService = groupService;
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
            model.addAttribute("groupedNoticeListMap", noticeService.getGroupedNoticeList(loginMember));
        } else{
            // redirection with login error
            Map<String,?> flashMap = RequestContextUtils.getInputFlashMap(req);

            if (flashMap!=null){
                try{
                    Map<String, String> errorMap = (Map<String, String>) flashMap.get("errorMap");
                    model.addAttribute("loginErrorMsg", errorMap.get("LoginError"));
                }catch(NullPointerException e){
                    System.err.println("null pointer error : main controller");
                }
            }
        }

        return "index";
    }
}



