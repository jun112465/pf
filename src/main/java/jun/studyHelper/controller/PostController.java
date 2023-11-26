package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.PostDto;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/post")
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
            @RequestBody PostDto postDTO,
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

    @GetMapping("/first")
    public String testModelAddController(RedirectAttributes redirectAttributes) {
        // 첫 번째 컨트롤러에서 모델 값 추가
        redirectAttributes.addFlashAttribute("exampleAttribute", "Hello from the first controller!");

        // 다른 링크로 리다이렉트
        return "redirect:/";
    }

    @GetMapping("/get")
    public String getUserPosts(
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "categoryId", required = false) String categoryId
    ) {

        CategoryDto categoryDto = CategoryDto.builder()
                .id(Long.parseLong(categoryId))
                .userId(userId)
                .build();

        List<PostDto> postDtoList = postService.convertPostListToDTO(
                postService.getPostsByCategory(categoryDto)
        );

        redirectAttributes.addFlashAttribute("posts", postDtoList);

        StringBuilder params = new StringBuilder();
        if(userId != null) params.append("?userId="+userId+"&");
        if(categoryId != null) params.append("categoryId="+categoryId);

        return "redirect:/" + params;
    }

    @PostMapping("/notice/add-category")
    public String addCategory(@RequestParam String categoryName, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        categoryService.addCategory(CategoryDto.builder()
                .userId(user.getUserId())
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
        CategoryDto categoryDTO = CategoryDto.builder()
                .id(Long.valueOf(id))
                .build();
        categoryService.deleteCategory(categoryDTO);

        return "redirect:/";
    }

}
