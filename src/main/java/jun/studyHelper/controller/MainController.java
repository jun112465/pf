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

    @Autowired
    MainController(MemberService memberService){
        this.memberService = memberService;
    }
    @GetMapping("/")
    public String rootController(Model model, @CookieValue(name="memberId", required = false)String memberId){

        try{
            System.out.println("MainController : memberId = " + memberId);
            model.addAttribute("memberId", memberId);
            model.addAttribute("noticeList",
                    memberService.findOne(Integer.valueOf(memberId)).noticeService.findNoticeList());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        return "root";
    }
}
