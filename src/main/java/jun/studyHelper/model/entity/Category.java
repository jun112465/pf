package jun.studyHelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    List<Post> postList;

    public Category(long id) {
        this.id = id;
    }
}
