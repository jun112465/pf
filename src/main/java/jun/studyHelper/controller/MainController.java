package jun.studyHelper.controller;

import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    MemberService memberService;
    NoticeService noticeService;

    @Autowired
    MainController(MemberService memberService, NoticeService noticeService){
        this.memberService = memberService;
        this.noticeService = noticeService;
    }
    @GetMapping("/")
    public String rootController(Model model, @CookieValue(name="memberId", required = false)String memberId){
        try{
            System.out.println("MainController : memberId = " + memberId);
            model.addAttribute("user", memberService.findOne(Integer.valueOf(memberId)));
            model.addAttribute("memberId", memberId);
            model.addAttribute("noticeList", noticeService.findNoticeList(Integer.valueOf(memberId)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        return "index";
    }

    @GetMapping("/page")
    public String myPageController(Model model, @CookieValue(name="memberId", required = false)String memberId){

        try{
            model.addAttribute("user", memberService.findOne(Integer.valueOf(memberId)));
            model.addAttribute("memberId", memberId);
            model.addAttribute("noticeList", noticeService.findNoticeList(Integer.valueOf(memberId)));
        }catch(Exception e){
            e.printStackTrace();
        }

        return "index";
    }
}
