package jun.studyHelper.entity;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Data
@Entity
public class Notice {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String memberId;

    int categoryId;

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
