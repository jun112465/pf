package jun.studyHelper.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Data
@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    String name;

    public Category(){
    }
    public Category(long id) {
        this.id = id;
    }
}
