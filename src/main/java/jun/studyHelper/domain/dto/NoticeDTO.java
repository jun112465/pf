package jun.studyHelper.domain.dto;
import jun.studyHelper.domain.entity.Category;
import jun.studyHelper.domain.entity.Member;
import lombok.Data;

import java.util.Date;

@Data
public class NoticeDTO {
    Member member;
    Category category;
    String content;
    Date date;
}
