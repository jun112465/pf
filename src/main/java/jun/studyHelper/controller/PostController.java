package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.PostDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.PostService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PostController {

    UserService userService;
    PostService postService;
    CategoryService categoryService;
    LoginService loginService;


    @Autowired
    public PostController(
            UserService userService,
            PostService postService,
            CategoryService categoryService,
            LoginService loginService
    ) {
        this.userService = userService;
        this.postService = postService;
        this.categoryService = categoryService;
        this.loginService = loginService;
    }


    @PostMapping("/post/add")
    public RedirectView addPost(
            @RequestBody PostDTO postDTO,
            @CookieValue(name=SessionConst.SESSION_ID, required = false) String sessionId
    ){
        postDTO.setDate(Post.getCurrentDate());
        postService.add(postDTO);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        redirectView.addStaticAttribute("userId", postDTO.getUserId());
        redirectView.addStaticAttribute("categoryId", postDTO.getCategoryId());

        return redirectView;
    }

    @GetMapping("notice/delete")
    public String deleteNote(@RequestParam String id){
        postService.delete(Long.valueOf(id));
        return "redirect:/";
    }

    @PostMapping("/post/update")
    @ResponseBody
    public void editNote(@RequestBody Post note){
        note.setContent(StringEscapeUtils.unescapeHtml4(note.getContent()));
        postService.editNote(note);
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

        postService.updateCategories(noticeCategory);
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
