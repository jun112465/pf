package jun.studyHelper.model.dto;

import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.repository.member.MemberRepository;
import jun.studyHelper.service.MemberService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    long id;
    String name;
    long memberId;
}
