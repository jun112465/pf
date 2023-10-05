package jun.studyHelper.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uid; // 변경 가능한 아이디 값
    private String pw;

    @Builder
    public User(String uid, String pw){
        this.uid = uid;
        this.pw = pw;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        User m = (User) obj;
        return this.uid.equals(m.getUid()) && this.pw.equals(m.getPw());
    }
}