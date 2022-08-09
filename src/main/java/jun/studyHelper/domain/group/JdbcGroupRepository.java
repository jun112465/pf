package jun.studyHelper.domain.group;

import jun.studyHelper.DBconfig;
import jun.studyHelper.domain.member.JdbcMemberRepository;
import jun.studyHelper.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGroupRepository implements GroupRepository{

    DBconfig db;

    // constructor
    @Autowired
    public void JdbcMemberRepository(DBconfig db){
        this.db = db;
    }


    @Override
    public void create(Group group) {
       try {
           String sql = String.format("INSERT INTO groups(id, name) VALUES(\"%s\", \"%s\")", group.getId(), group.getName());
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
    public void delete(Group group){
        try {
            String sql = String.format("DELETE FROM groups WHERE id=\"%s\"", group.getId());
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
    public void updateName(Group group) {
        try {
            String sql = String.format("UPDATE groups SET name=\"%s\"", group.getName());
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
            String sql = String.format("SELECT * FROM GROUPS WHERE id=\"%s\"", id);
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

        try {
            String sql = String.format("SELECT * FROM groups");

            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());


            List<Group> groupList = new ArrayList<>();
            while(db.getRs().next()){
                String id = db.getRs().getString("id");
                String name = db.getRs().getString("name");

                groupList.add(new Group(id, name));
            }

            return groupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }
    }


    @Override
    public List<Group> findAll(Group search) {


        try {

            String sql = String.format("SELECT * FROM groups WHERE id LIKE \"%s\" OR name LIKE \"%s\"", search.getId(), search.getName());

            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());


            List<Group> groupList = new ArrayList<>();
            while(db.getRs().next()){
                String id = db.getRs().getString("id");
                String name = db.getRs().getString("name");

                groupList.add(new Group(id, name));
            }

            return groupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }
    }
}
