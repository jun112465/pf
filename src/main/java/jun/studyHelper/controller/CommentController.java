package jun.studyHelper.controller;

import jun.studyHelper.model.dto.CommentAddRequest;
import jun.studyHelper.model.dto.CommentDto;
import jun.studyHelper.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    @ResponseBody
    public String addComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CommentAddRequest commentAddRequest
            ){
        commentAddRequest.setUserId(userDetails.getUsername());
        commentService.saveComment(commentAddRequest);

        StringBuilder params = new StringBuilder("?");
        if(commentAddRequest.getPageNo() != null)
            params.append("pageNo=" + commentAddRequest.getPageNo() + "&");
        if(commentAddRequest.getPageCategory() != null)
            params.append("categoryId=" + commentAddRequest.getPageCategory());

        return params.toString();
    }

}
