package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.dto.CategoryVO;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.Category;
import jun.studyHelper.service.CategoryService;
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
    CategoryService categoryService;

    @Autowired
    public NoticeController(MemberService memberService, NoticeService noticeService, CategoryService categoryService) {
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
    }

    @PostMapping("notice/add-note")
    @ResponseBody
    public boolean addNote(@RequestBody CategoryVO categoryVO, HttpServletRequest req){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Category category = categoryService.findCategory(new Category(categoryVO.getCategoryId()));

        Notice notice = new Notice();
        notice.setCategory(category);
        notice.setMember(member);

        noticeService.add(notice);

        return true;
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
        Category nc = new Category();
        nc.setMember(member);
        nc.setName(category);

        categoryService.addCategory(nc);

        return "redirect:/";
    }

    @PostMapping("/notice/update-category")
    @ResponseBody
    public void updateCategory(@RequestBody Category noticeCategory, HttpServletRequest req){
        System.out.println("LOG : noticeCategory : " + noticeCategory.toString());
        Member m = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        noticeCategory.setMember(m);

        noticeService.updateCategories(noticeCategory);
    }

    @GetMapping("/notice/delete-category")
    public String deleteCategory(HttpServletRequest req, String id){
        Member member = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Category nc = new Category();
        nc.setId(Integer.parseInt(id));
        noticeService.deleteNoticeListByCategory(member, nc);
        categoryService.deleteCategory(Long.valueOf(id));

        return "redirect:/";
    }

}
