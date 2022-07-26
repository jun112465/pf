package jun.studyHelper.domain.member;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Member {

    @NonNull
    private String memberId;
    private String password;
    private String name;
    private String profileImage;
    private String profileMessage;

    public Member(String memberId, String password){
        this.memberId = memberId;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        Member m = (Member) obj;
        return this.memberId.equals(m.memberId) && this.password.equals(m.password);
    }
}
