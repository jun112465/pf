package jun.studyHelper.domain.group;


import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private String id;
    @NonNull
    private String name;
}
