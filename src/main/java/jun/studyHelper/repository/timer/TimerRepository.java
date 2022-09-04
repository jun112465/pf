package jun.studyHelper.repository.timer;


import java.sql.Date;
import java.sql.Time;

public interface TimerRepository {

    void add(String memberId);
    void modify(String memberId, Date date, Time time);
}
