package jun.studyHelper.model.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    long id;
    long userId;
    long categoryId;
    String content;
    String html;
    String date;
}
