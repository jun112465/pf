package jun.studyHelper.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Data
@Entity
public class Notice {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn (name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    String date;

    public Notice() {
        setCurrentDate();
    }

    public void setCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.setDate(simpleDateFormat.format(new Date()));
    }
}
