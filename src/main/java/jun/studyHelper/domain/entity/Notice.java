package jun.studyHelper.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "member_id")
    String memberId;
    int categoryId;
    String content;
    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    Date date;

    public Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }
}
