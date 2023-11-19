package jun.studyHelper.model.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class UserLoginRequestDto {
    String userId;
    String password;
}
