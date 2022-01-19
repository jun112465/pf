package jun.studyHelper.domain.member;

import jun.studyHelper.domain.Database;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;


@Repository
public class JdbcMemberRepository implements MemberRepository{

    Database db = new Database();

    @Override
    public Member save(Member member) {
        String sql = String.format("insert into studyHelper.Member(memberId,name) values(%d, \"%s\")", member.getMemberId(), member.getName());
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

    @Override
    public void addFriend(Member me, Member friend) {

        String sql = String.format("Update studyHelper.Member Set friends = concat(friends,'-', %d) where memberId=%d", friend.getMemberId(), me.getMemberId() );

        db.setConnection(sql);

        try{
            db.getPs().executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, String> getFriends(Member me) {
        String sql = "Select friends from studyHelper.Member where memberId="+me.getMemberId();
        db.setConnection(sql);
        try{
            db.rs = db.getPs().executeQuery();
            db.rs.next();
            String friendString = db.rs.getString("friends");


            Map<Integer, String> list = new HashMap<>();
            String findMemberSql = "Select * from studyHelper.Member where memberId=%d";
            String [] arr = friendString.split("-");
            for(int i=1; i<arr.length; i++){
                int id = Integer.valueOf(arr[i]);
                db.rs = db.getPs().executeQuery(String.format(findMemberSql, id));
                db.rs.next();
                String name = db.rs.getString("name");
                list.put(id,name);
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
