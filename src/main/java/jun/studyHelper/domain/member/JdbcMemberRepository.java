package jun.studyHelper.domain.member;

import jun.studyHelper.DBconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
public class JdbcMemberRepository implements MemberRepository{
    DBconfig db;

    @Autowired
    JdbcMemberRepository(DBconfig db){
//        db = new DBconfig(dataSource);
        this.db = db;
    }

    @Override
    public Member save(Member member) {
        String sql = String.format("insert into studyHelper.Member(memberId,name) values(%d, \"%s\")", member.getMemberId(), member.getName());

        try {
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.ps.executeUpdate();
            return member;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }
        return null;
    }

    @Override
    public Member findById(int id) {
        String sql = String.format("select * from studyHelper.Member where memberId=%d", id);
        try{
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.rs = db.ps.executeQuery();
            db.rs.next();
            Member m = new Member();
            m.setMemberId(db.rs.getInt("memberId"));
            m.setName(db.rs.getString("name"));
            return m;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
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
        try{
            List<Member> members = new ArrayList<>();
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.rs = db.ps.executeQuery();

            while(db.rs.next()){
                Member member = new Member();
                member.setMemberId(db.rs.getInt("id"));
                member.setName(db.rs.getString("name"));
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
    public void addFriend(Member me, Member friend) {
        String sql = String.format("Update studyHelper.Member Set friends = concat(friends,'-', %d) where memberId=%d", friend.getMemberId(), me.getMemberId() );

        try {
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }

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
                int id = Integer.valueOf(arr[i]);
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
}
