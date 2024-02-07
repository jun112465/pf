package jun.studyHelper.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageInfo {
    int start;
    int end;
    int prevPageNo;
    int nextPageNo;
    int firstPageNo;
    int lastPageNo;
    int pageNo;
}
