package jun.studyHelper.controller;

import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeForm;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NoticeController {

    MemberService memberService;
    NoticeService noticeService;

    @Autowired
    NoticeController(MemberService memberService, NoticeService noticeService){
        this.memberService = memberService;
        this.noticeService = noticeService;
    }

    @PostMapping("notice/add-note")
    public String addNote(NoticeForm noticeForm, @CookieValue(name="memberId", required = false)String memberId){
        try{
            System.out.println("/add-note controller");
            System.out.println(noticeForm.getTitle());
            System.out.println(noticeForm.getContent());
            Notice n = new Notice();
            n.setContents(noticeForm.getContent());
            n.setMemberId(Integer.parseInt(memberId));
            n.setTitle(noticeForm.getTitle());
            noticeService.add(n);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @PostMapping(value="/schedule/delete", produces = "application/json")
    @ResponseBody
    public List<Notice> deleteNote(NoticeForm form, @CookieValue(name = "memberId", required = false)String memberId) {
        noticeService.delete(form.getId());
        return noticeService.findNoticeList(Integer.parseInt(memberId));
    }

    @GetMapping("/schedule/sendFriendSchedule")
    @ResponseBody
    public List<Notice> sendFriendSchedule(@RequestParam("memberId")int memberId){
        System.out.println(memberId);
        return noticeService.findNoticeList(memberId);
    }
}
