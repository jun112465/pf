package jun.studyHelper.domain.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMember {
    String groupId;
    String userId;
}
