package jun.studyHelper.domain.notice;

import jun.studyHelper.DBconfig;
import jun.studyHelper.domain.member.Member;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcNoticeRepository implements NoticeRepository{
    DBconfig db;

    @Autowired
    JdbcNoticeRepository(DBconfig db){
        this.db = db;
    }

    @Override
    public void save(Notice notice) {
        try {
            String sql = String.format(
                    "INSERT INTO notice(member_id) VALUES(\"%s\")",
                    notice.getMemberId());
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
    public void update(Notice notice) {
        try {
            String sql = String.format("UPDATE notice SET content=\"%s\" WHERE id=%d", notice.getContent(), notice.getId());
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
    public void remove(int id) {
        try {
            String sql = String.format(
                    "DELETE FROM notice WHERE id=\"%d\"", id);
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
    public List<Notice> findAll() {
        List<Notice> notices = null;
        try {
            String sql = String.format("SELECT * FROM notice");
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());

            notices = new ArrayList<>();
            while (db.getRs().next()) {
                int id = db.getRs().getInt("id");
                String memberId = db.getRs().getString("member_id");
                String content = db.getRs().getString("content");
                String date = String.valueOf(db.getRs().getDate("date"));

                Notice notice = new Notice(id, memberId, content, date);
                notices.add(notice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
            return notices;
        }
    }

    @Override
    public List<Notice> findByMemberId(String memberId) {
        List<Notice> notices = null;
        try {
            String sql = String.format("SELECT * FROM notice WHERE member_id=\"%s\" ORDER BY date DESC", memberId);
            db.setConn(db.getConnection());
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());

            notices = new ArrayList<>();
            while (db.getRs().next()) {
                int id = db.getRs().getInt("id");
                String content = db.getRs().getString("content");
                String date = String.valueOf(db.getRs().getDate("date"));

                Notice notice = new Notice(id, memberId, content, date);
                notices.add(notice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
            return notices;
        }
    }

    @Override
    public Notice findRecentMemberNotice(Member member, String date) {

        String sql = String.format(
                "SELECT * FROM notice WHERE member_id=\"%s\" and date=\"%s\"",
                member.getMemberId(), date);

        db.setConn(db.getConnection());
        try {
            db.setPs(db.getConn().prepareStatement(sql));
            db.setRs(db.getPs().executeQuery());

            db.getRs().next();

            Notice notice = new Notice(
                    db.getRs().getInt("id"),
                    db.getRs().getString("member_id"),
                    db.getRs().getString("content"),
                    db.getRs().getString("date"));

            return notice;
        } catch (SQLDataException e){
          return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(db.getConn(), db.getPs(), db.getRs());
        }
    }
}
