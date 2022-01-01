package jun.studyHelper.domain.notice;

import jun.studyHelper.domain.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcNoticeRepository implements NoticeRepository{

    Database db = new Database();

    @Override
    public void save(Notice notice) {
        String sql = String.format("insert into studyHelper.Notice(memberId, content) values(%d, \"%s\")",
                notice.memberId, notice.content);
        db.setConnection(sql);
        try {
            db.getPs().executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Notice> findAll(int memberId) {

        ArrayList<Notice> noticeList = new ArrayList<>();
        String sql = String.format("Select * from studyHelper.Notice where memberId=%d", memberId);
        db.setConnection(sql);
        try {
            ResultSet rs = db.getRs();
            rs = db.getPs().executeQuery();
            while(rs.next()){
                Notice notice = new Notice();

                notice.setMemberId(rs.getInt("memberId"));
                notice.setContents(rs.getString("content"));
                notice.setDate(rs.getDate("date"));

                noticeList.add(notice);
            }
            db.closeConnection();
            return noticeList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
