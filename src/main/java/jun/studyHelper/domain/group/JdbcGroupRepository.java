package jun.studyHelper.domain.group;

import jun.studyHelper.DBconfig;
import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class JdbcGroupRepository implements GroupRepository{

    DBconfig db;

    // constructor
    @Autowired
    public void JdbcMemberRepository(DBconfig db){
        this.db = db;
    }


    @Override
    public void create(Group group) {

        String sql = String.format("INSERT INTO groups(id, name) VALUES(\"%s\", \"%s\")", group.getId(), group.getName());

        try {
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
    public Group findById(String id) {
        try {
            String sql = String.format("SELECT * FROM GROUP WHERE id=\"%s\"");
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());
            while(db.getRs().next()){
                String name = db.getRs().getString("name");
                return new Group(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            db.close(db.getConn(), db.getPs(), db.getRs());
        }

        return null;
    }

    @Override
    public List<Group> findAll() {
        return null;
    }

    @Override
    public void addMember(String groupId, Member member) {

    }
}
