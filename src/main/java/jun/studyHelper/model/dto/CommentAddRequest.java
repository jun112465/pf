package jun.studyHelper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAddRequest extends CommentDto{
    Integer pageNo;
    Long pageCategory;
}
