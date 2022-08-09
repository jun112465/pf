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

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        conn = null;
//        ps = null;
//        rs = null;
//        return DataSourceUtils.getConnection(dataSource);
        //이 방법 사용시 connection error 뜬다.
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet rs){

        try {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
}
