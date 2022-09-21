package jun.studyHelper.domain.group;

import jun.studyHelper.DBconfig;
import jun.studyHelper.domain.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGroupMemberRepository implements GroupMemberRepository{

    DBconfig db;

    @Autowired
    public JdbcGroupMemberRepository(DBconfig db){
        this.db = db;
    }

    @Override
    public void add(Member member, Group group) {

        try {
            String sql = String.format(
                    "INSERT INTO group_members(group_id, member_id) " +
                            "VALUES(\"%s\", \"%s\")", group.getId(), member.getId());

            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.getPs().executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }
    }

    @Override
    public void delete(Member member, Group group) {

    }

    @Override
    public List<Member> findAllMember(Group group) {

        return null;
    }

    @Override
    public List<Group> findAllGroups(Member member) {

        String sql = String.format("SELECT * FROM group_members WHERE member_id = \"%s\"", member.getId());

        try {
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());

            List<Group> groupList = new ArrayList<>();
            while(db.getRs().next()){
                String groupId = db.getRs().getString("group_id");
                String memberId = db.getRs().getString("member_id");
                groupList.add(new Group(groupId, memberId));
            }

            return groupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }
    }
}
