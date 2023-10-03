package jun.studyHelper.repository;

import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByParentComment(Comment parentComment);
    List<Comment> findByPostId(long noticeId);
    List<Comment> findByPostIdAndParentCommentIdIsNull(long noticeId);
    List<Comment> findByPostAndParentCommentIsNull(Post post);
}
