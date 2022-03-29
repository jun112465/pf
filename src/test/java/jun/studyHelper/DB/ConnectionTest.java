package jun.studyHelper.DB;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


//SpringBootTest 는 실제 애플리케이션을 자신의 로컬 위에 올려서 포트 주소가 Listening 되어지고, 실제 Database 와 커넥션이 붙어지는
//상태에서 진행되는 Live 테스트 방법
@SpringBootTest
public class ConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("DB 연결확인")
    public void connectionTest() throws SQLException {
        Connection con = dataSource.getConnection();
        System.out.println(con);
        System.out.println();
    }
}
