package jun.studyHelper.service;


import jun.studyHelper.model.dto.CommentDto;
import jun.studyHelper.model.dto.PostDto;
import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.repository.CommentRepository;
import jun.studyHelper.repository.post.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 댓글 저장
    public Comment saveComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .user(userRepository.findById(commentDto.getUserId()).get())
                .post(postRepository.getById(commentDto.getPostId()))
                .content(commentDto.getContent())
                .build();
        return commentRepository.save(comment);
    }

    // 대댓글 저장
    public void saveChildComment(Comment comment){
        Comment parentComment = comment.getParentComment();// 부모 댓글 객체 가져오기

        // 대댓글의 부모 설정
//        Comment newChildComment = new Comment();
//        newChildComment.setContent("대댓글 내용");
//        newChildComment.setParentComment(parentComment);
//
//        // 부모댓글의 리스트에 자식댓글 추가
//        parentComment.getChildren().add(newChildComment);
    }

    // 댓글 조회
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    // 대댓글 조회
    public List<Comment> getChildComments(Comment parentComment) {
        return commentRepository.findByParentComment(parentComment);
    }


    // 노트의 모든 댓글 가져오기
    public List<Comment> getNoteComments(PostDto postDTO){
        commentRepository.findByPostId(postDTO.getId());
        return null;
    }
}
