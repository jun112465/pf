package jun.studyHelper.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    long id;
    String uid;
    String pwd;
}
