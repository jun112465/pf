package jun.studyHelper.model.dto;

import jun.studyHelper.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    String content;
    String userId;
    Long postId;
}
