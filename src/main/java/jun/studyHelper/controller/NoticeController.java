package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.domain.notice.NoticeForm;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeCategoryService;
import jun.studyHelper.service.NoticeService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class NoticeController {

    MemberService memberService;
    NoticeService noticeService;
    NoticeCategoryService noticeCategoryService;

    @Autowired
    public NoticeController(MemberService memberService, NoticeService noticeService, NoticeCategoryService noticeCategoryService) {
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.noticeCategoryService = noticeCategoryService;
    }

    @PostMapping("notice/add-note")
    @ResponseBody
    public boolean addNote(HttpServletRequest req){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        Notice notice = new Notice();
        notice.setMemberId(member.getId());
        notice.setDate(new Date(System.currentTimeMillis()));

        System.out.println("LOG : notice -> " + notice);
        if(noticeService.add(notice, member) == null)
            return false;
        else
            return true;
    }

    @PostMapping("/notice/update")
    @ResponseBody
    public void editNote(@RequestBody Notice note){
        note.setContent(StringEscapeUtils.unescapeHtml4(note.getContent()));
        noticeService.editNote(note);
    }


    @PostMapping("/notice/add-category")
    @ResponseBody
    public void addCategory(@RequestParam String category, HttpServletRequest req){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        NoticeCategory nc = new NoticeCategory();
        nc.setMemberId(member.getId());
        nc.setCategory(category);

        noticeCategoryService.addCategory(nc);
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
