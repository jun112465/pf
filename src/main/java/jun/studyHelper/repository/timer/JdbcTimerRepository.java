package jun.studyHelper.repository.timer;

import jun.studyHelper.DBconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.*;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;


@Repository
public class JdbcTimerRepository implements TimerRepository{

//    @Autowired
//    DBconfig db;
    private final DataSource dataSource;
    private final DBconfig db;

    @Autowired
    public JdbcTimerRepository(DBconfig db, DataSource dataSource){
        this.dataSource = dataSource;
        this.db = db;
    }


    @Override
    public void add(String memberId) {

        Connection conn = null;
        PreparedStatement ps;

        try {
            String sql = String.format("INSERT INTO timer(member_id) VALUES('%s')", memberId);
            System.out.println(sql);

//            conn = getConnection();

            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.getPs().executeUpdate();
//            ps = conn.prepareStatement(sql);
//            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modify(String memberId, Date date, Time time) {

    }

    private Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }


    //            db.setConn(db.getConnection());
//            db.setPs(db.getConn().prepareStatement(sql));
//            db.getPs().executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            db.close(db.getConn(), db.getPs(), db.getRs());
//        }
    }
//
//    @Override
//    public void modify(String memberId, Date date, Time time) {
//        try {
//            String sql = String.format("UPDATE TABLE timer SET time='%s' WHERE memberId='%s' and date='%s'", time, memberId, date);
//            db.setConn(db.getConnection());
//            db.setPs(db.getConn().prepareStatement(sql));
//            db.getPs().executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            db.close(db.getConn(), db.getPs(), db.getRs());
//        }
//    }
