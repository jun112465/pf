package jun.studyHelper.domain.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notice {
    int id;
    String memberId;
    String content;
    String date;
}
