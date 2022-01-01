package jun.studyHelper.controller;

import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeForm;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

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
            Notice n = new Notice();
            n.setContents(noticeForm.getContent());
            n.setMemberId(Integer.valueOf(memberId));
            n.setTitle(noticeForm.getTitle());
            noticeService.add(n);

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
