package jun.studyHelper.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "notice")
@DynamicInsert
@DynamicUpdate
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "member_id")
    String memberId;
    String content;
    @Temporal(TemporalType.DATE)
    Date date;
}
