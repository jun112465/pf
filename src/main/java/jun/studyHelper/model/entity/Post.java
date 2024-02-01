package jun.studyHelper.model.entity;

import jun.studyHelper.model.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id desc")
    List<Comment> comments;



    String date;

    public static String getCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public List<CommentDto> getCommentDtoList(){
        return comments.stream()
                .map(Comment::convertToDto)
                .collect(Collectors.toList());
    }
}
