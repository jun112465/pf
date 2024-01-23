package jun.studyHelper.model.entity;

import jun.studyHelper.model.dto.CommentDto;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @JoinColumn(name = "post_id")
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
                //post 순환참조 stackOverflow 방지
                ", post=" + post.getId() + "\n" +
                ", content='" + content + '\'' +
//                ", parentComment=" + parentComment +
//                ", children=" + children +
                ", date=" + date +
                '}';
    }

    public static CommentDto convertToDto(Comment comment){
        return CommentDto.builder()
                .content(comment.content)
                .userId(comment.user.getUserId())
                .postId(comment.post.getId())
                .build();
    }

}
