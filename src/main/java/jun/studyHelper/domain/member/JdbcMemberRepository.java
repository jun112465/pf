package jun.studyHelper.domain.member;

import jun.studyHelper.DBconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcMemberRepository implements MemberRepository{
    DBconfig db;
    DataSource dataSource;

    @Autowired
    public JdbcMemberRepository(DBconfig db, DataSource dataSource){
//        db = new DBconfig(dataSource);
        this.dataSource = dataSource;
        this.db = db;
    }

    @Override
    public Member save(Member member) {
        String sql = String.format(
                "INSERT INTO " +
                "member(member_id, password) " +
                "VALUES(\"%s\", \"%s\")",
                member.getMemberId(), member.getPassword());

        try {
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.ps.executeUpdate();
            return member;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }
    }

    @Override
    public Member findById(String id) {
        String sql = String.format("SELECT * FROM member WHERE member_id=\"%s\"", id);


        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();

            String m_id = rs.getString("member_id");
            String m_pw = rs.getString("password");
            Member m = new Member();
            m.setMemberId(m_id);
            m.setPassword(m_pw);

            return m;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        try{
            List<Member> members = new ArrayList<>();
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.rs = db.ps.executeQuery();

            while(db.rs.next()){
                Member member = new Member(db.rs.getString("member_id"));
                members.add(member);
            }
            return members;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }
        return null;
    }



    @Override
    public Map<Integer, String> getFriends(Member me) {
        String sql = "Select friends from studyHelper.Member where memberId="+me.getMemberId();
        try {
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.rs = db.ps.executeQuery();
            db.rs.next();

            String friendString = db.rs.getString("friends");
            Map<Integer, String> list = new HashMap<>();
            String findMemberSql = "Select * from studyHelper.Member where memberId=%d";
            String [] arr = friendString.split("-");
            for(int i=1; i<arr.length; i++){
                int id = Integer.parseInt(arr[i]);
                db.rs = db.ps.executeQuery(String.format(findMemberSql, id));
                db.rs.next();
                String name = db.rs.getString("name");
                list.put(id,name);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }
        return null;
    }

    @Override
    public void updateMemberInfo(String profileFileName, String profileMessage, int memberId) {
        db.conn = db.getConnection();
        try{
            if(profileFileName != null) {
                String sql = String.format("update Member set profileImage='%s' where memberId=%d", profileFileName, memberId);
                db.ps = db.conn.prepareStatement(sql);
                db.ps.executeUpdate();
            }

            if(profileMessage != null){
                String sql = String.format("update Member set profileMessage='%s' where memberId=%d", profileMessage, memberId);
                db.ps = db.conn.prepareStatement(sql);
                db.ps.executeUpdate();
            }
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }
    }
}











