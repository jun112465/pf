package jun.studyHelper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDeleteRequest extends PostDto {
    Integer pageNo;
    Long pageCategory;
}
