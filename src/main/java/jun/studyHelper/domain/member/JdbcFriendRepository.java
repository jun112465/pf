package jun.studyHelper.domain.member;

import jun.studyHelper.DBconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcFriendRepository implements FriendRepository{

    DBconfig db;

    @Autowired
    public JdbcFriendRepository(DBconfig db) {
        this.db = db;
    }

    @Override
    public void add(Member me, Member friend) {
        String sql = String.format("INSERT INTO friend SET member_id=\"%s\", friend_id=\"%s\"");

        try {
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.getPs().executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }

    }

    @Override
    public void delete(Member me, Member friend) {
        String sql = String.format("DELETE FROM friend WHERE member_id=\"%s\", friend_id=\"%s\"");

        try{
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.getPs().executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }
    }

    @Override
    public List<Member> findAll(Member me) {
        String sql = String.format("SELECT * FROM friend WHERE member_id=\"%s\"");

        try {
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());

            List<Member> friendList = new ArrayList<>();
            while(db.getRs().next()){
                String friendId = db.getRs().getString("friend_id");
                Member m = new Member(friendId);
                friendList.add(m);
            }

            return friendList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }

    }


}
