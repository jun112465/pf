package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.NoticeService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
public class NoticeController {

    UserService userService;
    NoticeService noticeService;
    CategoryService categoryService;

    @Autowired
    public NoticeController(UserService userService, NoticeService noticeService, CategoryService categoryService) {
        this.userService = userService;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
    }

    @PostMapping("notice/add-note")
    @ResponseBody
    public boolean addNote(@RequestBody CategoryDTO categoryDTO, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .userId(user.getId())
                .categoryId(categoryDTO.getId())
                .content("note")
                .date(Notice.getCurrentDate())
                .build();

        noticeService.add(noticeDTO);

        return true;
    }

    @GetMapping("notice/delete")
    public String deleteNote(@RequestParam String id){
        noticeService.delete(Long.valueOf(id));
        return "redirect:/";
    }

    @PostMapping("/notice/update")
    @ResponseBody
    public void editNote(@RequestBody Notice note){
        note.setContent(StringEscapeUtils.unescapeHtml4(note.getContent()));
        noticeService.editNote(note);
    }


    @PostMapping("/notice/add-category")
    public String addCategory(@RequestParam String categoryName, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        categoryService.addCategory(CategoryDTO.builder()
                .userId(user.getId())
                .name(categoryName)
                .build());

        return "redirect:/";
    }

    @PostMapping("/notice/update-category")
    @ResponseBody
    public void updateCategory(@RequestBody Category noticeCategory, HttpServletRequest req){
        System.out.println("LOG : noticeCategory : " + noticeCategory.toString());
        User m = (User) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        noticeCategory.setUser(m);

        noticeService.updateCategories(noticeCategory);
    }

    @GetMapping("/notice/delete-category")
    public String deleteCategory(HttpServletRequest req, String id){
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(Long.valueOf(id))
                .build();
        categoryService.deleteCategory(categoryDTO);

        return "redirect:/";
    }

}
