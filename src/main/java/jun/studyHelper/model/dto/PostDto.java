package jun.studyHelper.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    long id;
    String userId;
    long categoryId;
    String content;
    String html;
    String date;

    List<CommentDto> comments;
}
