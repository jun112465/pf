package jun.studyHelper.model.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostDto {
    long id;
    String userId;
    long categoryId;
    String content;
    String html;
    String date;

    List<CommentDto> comments;
}
