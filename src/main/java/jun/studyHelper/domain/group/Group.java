package jun.studyHelper.domain.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Group {
    private String id;
    private String name;
}
