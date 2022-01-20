package jun.studyHelper.domain.notice;

import jun.studyHelper.DBconfig;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
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
            db.conn = db.getConnection();
            String sql = String.format("insert into studyHelper.Notice(memberId, content, title) values(%d, \"%s\", \"%s\")",
                    notice.memberId, notice.content, notice.title);
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            db.close(db.conn, db.ps, db.rs);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Notice> findAll(int memberId) {
        ArrayList<Notice> noticeList = new ArrayList<>();
        String sql = String.format(
                "Select * from studyHelper.Notice where " +
                        "memberId=%d order by date desc"
                , memberId);

        try {
            db.conn = db.getConnection();
            db.ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            db.rs = db.ps.executeQuery();
            while(db.rs.next()){
                Notice notice = new Notice();

                notice.setTitle(db.rs.getString("title"));
                notice.setMemberId(db.rs.getInt("memberId"));
                String tmpContent = db.rs.getString("content");
                notice.setContents(tmpContent);
                notice.setContents(StringEscapeUtils.unescapeHtml4(tmpContent));
                notice.setDate(db.rs.getDate("date"));

                noticeList.add(notice);
            }
            return noticeList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            db.close(db.conn, db.ps, db.rs);
        }
        return null;
    }
}
