package jun.studyHelper.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Member {

    @NonNull
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String pw;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        Member m = (Member) obj;
        return this.id.equals(m.id) && this.pw.equals(m.pw);
    }
}
