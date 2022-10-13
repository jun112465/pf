package jun.studyHelper.domain.entity;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private String id;
    @NonNull
    private String name;


    static public String createGroupId(){

        List<String> candidate = new ArrayList<>();
        for(int i=0; i<36; i++)
            if(i < 26) candidate.add(Character.toString('a'+i));
            else candidate.add(Integer.toString(i-26));


        String id = new String();
        for(int i=0; i<5; i++) {
            int idx = Math.abs(new Random().nextInt()) % 36;
            System.out.println(candidate.get(idx));
            id += candidate.get(idx);
        }

        return id;
    }
}
