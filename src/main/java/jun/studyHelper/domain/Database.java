package jun.studyHelper.domain;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

public class Database {

    // MySQL Connector 의 클래스. DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // DB 경로
    private static final String URL = "jdbc:mysql://localhost:3306/Board?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "1q2w3e4r!";

    private Connection conn = null;
    private PreparedStatement ps = null;
    public ResultSet rs = null;


    public void setConnection(String sql){
        conn = null;
        ps = null;
        rs = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            rs.close();
            ps.close();
            conn.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
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
