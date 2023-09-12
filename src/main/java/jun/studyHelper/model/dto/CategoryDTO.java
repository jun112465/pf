package jun.studyHelper.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    long id;
    String name;
    long userId;
}
