package jun.studyHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBconfig {
    private final DataSource dataSource;
    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    public DBconfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Connection getConnection(){
        conn = null;
        ps = null;
        rs = null;
        return DataSourceUtils.getConnection(dataSource);
    }
    public void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try{
            if(pstmt != null){
                pstmt.close();
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
