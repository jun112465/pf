package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.domain.notice.NoticeForm;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @ResponseBody
    public boolean addNote(HttpServletRequest req){
        try{
            Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
            if(!noticeService.isTodayNoticeAdded(member)){
                Notice notice = new Notice();
                notice.setMemberId(member.getMemberId());
                noticeService.add(notice);
                return true;
            }else{
                return false;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/notice/update")
    @ResponseBody
    public void editNote(@RequestBody Notice note){
        note.setContent(StringEscapeUtils.unescapeHtml4(note.getContent()));
        noticeService.editNote(note);
    }

    @PostMapping(value="/schedule/delete")
    public String deleteNote(NoticeForm form, @CookieValue(name = "memberId", required = false)String memberId) {
        noticeService.delete(form.getId());
        return "redirect:/";
    }

//    @GetMapping("/schedule/sendFriendSchedule")
//    @ResponseBody
//    public List<Notice> sendFriendSchedule(@RequestParam("memberId")int memberId){
////        model.addAttribute("friendNoticeList", noticeService.findNoticeList(memberId));
//        System.out.println(memberId);
//        return noticeService.findNoticeList(memberId);
////        return "index";
//    }
}
