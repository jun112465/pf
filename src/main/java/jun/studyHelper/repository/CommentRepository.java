package jun.studyHelper.repository;

import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByParentComment(Comment parentComment);
    List<Comment> findByNoticeId(long noticeId);
    List<Comment> findByNoticeIdAndParentCommentIdIsNull(long noticeId);
    List<Comment> findByNoticeAndParentCommentIsNull(Notice notice);
}
