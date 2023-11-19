package jun.studyHelper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    //JwtToken 의 필드 중  grantType 는 JWT 에 대한 인증 타입이다.
    //단순하고 직관적이며 널리 사용되는 "Bearer" 인증 방식을 사용할 것이다.
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
