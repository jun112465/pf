package jun.studyHelper.DB.timer;


import jun.studyHelper.repository.timer.JdbcTimerRepository;
import jun.studyHelper.repository.timer.TimerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TimerTest {


    @Autowired
    TimerRepository timerRepository;

    @Test
    @DisplayName("타이머에 대한 기본적인 crud 테스트 진행")
    @Transactional
    public void crudTest(){
        timerRepository.add("ktop1017");
    }
}
