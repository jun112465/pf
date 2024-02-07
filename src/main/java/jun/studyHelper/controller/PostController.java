package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.PageInfo;
import jun.studyHelper.model.dto.PostDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.LoginService;
import jun.studyHelper.service.UserService;
import jun.studyHelper.service.PostService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
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


    @PostMapping("/add")
    @ResponseBody
    public String addPost(
            @RequestBody PostDto postDTO
    ){
        postDTO.setDate(Post.getCurrentDate());
        postDTO.setContent("");
        postService.add(postDTO);

        String redirectPath = "/post/get?categoryId="+postDTO.getCategoryId();
        return redirectPath;
    }

    @GetMapping("/delete/{postId}")
    public String deleteNote(
            @PathVariable String postId,
            @AuthenticationPrincipal UserDetails userDetails){

        User deleteUser = userService.findUser(UserDto.builder().userId(userDetails.getUsername()).build()).get();
        if(postService.validateDeletePostUser(deleteUser, Long.valueOf(postId)))
            postService.delete(Long.valueOf(postId));

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
//            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "categoryId", required = false) String reqCategoryId,
            @RequestParam(value = "pageNo", required = false) String reqPageNo
    ) {
//        List<PostDto> postDtoList = postService.convertPostListToDTO(
//                postService.getPostsByCategory(categoryDto)
//        );
        int totalPage;
        int pageNo;
        PageInfo pageInfo;
        List<PostDto> postDtoList;
        Long categoryId = null;

        if(reqPageNo == null) pageNo = 1;
        else pageNo = Integer.parseInt(reqPageNo);

        if(reqCategoryId != null){
            categoryId = Long.parseLong(reqCategoryId);
            totalPage = postService.getTotalPage(categoryId);
            postDtoList = postService.getPostPage(pageNo, categoryId);
            pageInfo = postService.getPageRange(pageNo, categoryId);
        }
        else{
            totalPage = postService.getTotalPage();
            postDtoList = postService.getPostPage(pageNo);
            pageInfo = postService.getPageRange(pageNo);
        }

        redirectAttributes.addFlashAttribute("posts", postDtoList);
        redirectAttributes.addFlashAttribute("totalPage", totalPage);
        redirectAttributes.addFlashAttribute("pageInfo", pageInfo);
        redirectAttributes.addFlashAttribute("pageNo", pageNo);
        redirectAttributes.addFlashAttribute("categoryId", categoryId);

        // redirection
        StringBuilder params = new StringBuilder();
        if(categoryId != null) params.append("?categoryId="+categoryId);

        return "redirect:/" + params;
    }



}
