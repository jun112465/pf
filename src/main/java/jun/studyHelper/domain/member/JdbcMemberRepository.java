package jun.studyHelper.domain.member;

import jun.studyHelper.domain.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class JdbcMemberRepository implements MemberRepository{

    Database db = new Database();

    @Override
    public Member save(Member member) {
        String sql = String.format("insert into studyHelper.Member(id,name) values(%d, \"%s\")", member.getMemberId(), member.getName());
        System.out.println(sql);

        try{
            db.setConnection(sql);
            db.getPs().executeUpdate();
            return member;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Member findById(int id) {
        String sql = String.format("select * from studyHelper.Member where memberId=%d", id);
        db.setConnection(sql);
        try{
            db.rs = db.getPs().executeQuery();
            db.rs.next();

            Member m = new Member();
            m.setMemberId(db.rs.getInt("memberId"));
            m.setName(db.rs.getString("name"));
            db.closeConnection();
            return m;
        }catch(SQLException e){
          e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from studyHelper.Member";
        db.setConnection(sql);

        try{
            List<Member> members = new ArrayList<>();
            ResultSet rs = db.getPs().executeQuery();
            while(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            db.closeConnection();
            return members;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
