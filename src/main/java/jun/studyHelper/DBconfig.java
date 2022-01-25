package jun.studyHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Configuration
public class DBconfig {
    private final DataSource dataSource;
    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;


    public DBconfig(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public Connection getConnection(){
//        conn = null;
//        ps = null;
//        rs = null;
//        return DataSourceUtils.getConnection(dataSource);
        //이 방법 사용시 connection error 뜬다.
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public void close(Connection conn, PreparedStatement ps, ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try{
            if(ps != null){
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try{
            if(conn != null){
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
