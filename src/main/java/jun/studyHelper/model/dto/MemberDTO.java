package jun.studyHelper.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    long id;
    String uid;
    String pwd;
}
