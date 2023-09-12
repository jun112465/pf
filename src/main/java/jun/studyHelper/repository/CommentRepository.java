package jun.studyHelper.repository;

import jun.studyHelper.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByParentComment(Comment parentComment);
}
