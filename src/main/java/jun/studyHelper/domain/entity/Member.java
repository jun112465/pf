package jun.studyHelper.domain.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uid; // 변경 가능한 아이디 값
    private String pw;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        Member m = (Member) obj;
        return this.uid.equals(m.getUid()) && this.pw.equals(m.getPw());
    }
}
