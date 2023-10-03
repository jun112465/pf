package jun.studyHelper.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "notice_id")
    @ManyToOne
    private Post post;

    private String content;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Comment parentComment;

    @OneToMany(
            mappedBy = "parentComment",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Comment> children;

    private Date date;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", content='" + content + '\'' +
//                ", parentComment=" + parentComment +
//                ", children=" + children +
                ", date=" + date +
                '}';
    }

}
