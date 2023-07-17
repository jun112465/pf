package jun.studyHelper.model.dto;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class NoticeDTO {
    long memberId;
    long categoryId;
    long noticeId;
    String content;
    String date;
}
