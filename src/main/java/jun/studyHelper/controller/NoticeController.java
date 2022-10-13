package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.NoticeCategory;
import jun.studyHelper.domain.dto.Category;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeCategoryService;
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
    NoticeCategoryService noticeCategoryService;

    @Autowired
    public NoticeController(MemberService memberService, NoticeService noticeService, NoticeCategoryService noticeCategoryService) {
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.noticeCategoryService = noticeCategoryService;
    }

    @PostMapping("notice/add-note")
    @ResponseBody
    public boolean addNote(@RequestBody Category category, HttpServletRequest req){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        Notice notice = new Notice();
        notice.setCategoryId(Integer.parseInt(category.getCategoryId()));
        notice.setMemberId(member.getId());

        if(noticeService.add(notice) == null) return false;
        else return true;
    }

    @PostMapping("/notice/update")
    @ResponseBody
    public void editNote(@RequestBody Notice note){
        note.setContent(StringEscapeUtils.unescapeHtml4(note.getContent()));
        noticeService.editNote(note);
    }


    @PostMapping("/notice/add-category")
    public String addCategory(@RequestParam String category, HttpServletRequest req){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        System.out.println("LOG: category : " + category);
        NoticeCategory nc = new NoticeCategory();
        nc.setMemberId(member.getId());
        nc.setCategory(category);

        noticeCategoryService.addCategory(nc);

        return "redirect:/";
    }

    @PostMapping("/notice/update-category")
    @ResponseBody
    public void updateCategory(@RequestBody NoticeCategory noticeCategory, HttpServletRequest req){
        System.out.println("LOG : noticeCategory : " + noticeCategory.toString());
        Member m = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        noticeCategory.setMemberId(m.getId());

        noticeService.updateCategories(noticeCategory);
    }

    @GetMapping("/notice/delete-category")
    public String deleteCategory(HttpServletRequest req, String id){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        NoticeCategory nc = new NoticeCategory();
        nc.setId(Integer.parseInt(id));
        noticeService.deleteNoticeListByCategory(member, nc);
        noticeCategoryService.deleteCategory(Integer.parseInt(id));

        return "redirect:/";
    }

}
