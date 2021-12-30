package jun.studyHelper.domain.member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;


public class JdbcMemberRepository implements MemberRepository{


    // MySQL Connector 의 클래스. DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // DB 경로
    private static final String URL = "jdbc:mysql://localhost:3306/Board?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "1q2w3e4r!";

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

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
        }
    }

    @Override
    public Member save(Member member) {
        String sql = String.format("insert into studyHelper.Member(id,name) values(%d, \"%s\")", member.getId(), member.getName());
        System.out.println(sql);

        try{
            setConnection(sql);
            ps.executeUpdate();
            return member;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Member findById(int id) {
        return null;
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from studyHelper.Member";
        setConnection(sql);

        try{
            List<Member> members = new ArrayList<>();
            while(rs.next()){
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            closeConnection();
            return members;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
