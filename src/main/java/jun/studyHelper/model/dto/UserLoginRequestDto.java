package jun.studyHelper.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {
    String userId;
    String password;

    public UserLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
