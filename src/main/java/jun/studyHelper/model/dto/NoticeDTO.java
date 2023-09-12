package jun.studyHelper.model.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeDTO {
    long userId;
    long categoryId;
    long noticeId;
    String content;
    String date;
}
