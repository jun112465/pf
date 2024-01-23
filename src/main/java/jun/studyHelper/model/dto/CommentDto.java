package jun.studyHelper.model.dto;

import jun.studyHelper.model.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    String content;
    String userId;
    Long postId;
}
