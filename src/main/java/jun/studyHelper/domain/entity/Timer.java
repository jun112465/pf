package jun.studyHelper.domain.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@RequiredArgsConstructor
public class Timer {
    @NonNull
    String memberId;
    Date date;
    Time time;
}
