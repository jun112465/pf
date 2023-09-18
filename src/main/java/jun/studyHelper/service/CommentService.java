package jun.studyHelper.service;


import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.repository.CommentRepository;
import jun.studyHelper.repository.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, NoticeRepository noticeRepository) {
        this.commentRepository = commentRepository;
        this.noticeRepository = noticeRepository;
    }

    // 댓글 저장
    public Comment saveComment(Comment comment) {
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
    public List<Comment> getNoteComments(NoticeDTO noticeDTO){
        commentRepository.findByNoticeId(noticeDTO.getNoticeId());
        return null;
    }
}
