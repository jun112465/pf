package jun.studyHelper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
public class MemberDTO {
    long id;
    String uid;
    String password;


}
