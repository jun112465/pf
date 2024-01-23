package jun.studyHelper.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordChangeRequest {
    String currentPassword;
    String newPassword;
    String confirmPassword;
}
