package jun.studyHelper.controller;

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

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CommentDto commentDto
    ){
        commentDto.setUserId(userDetails.getUsername());

        commentService.saveComment(commentDto);
        return "main";
    }

}
