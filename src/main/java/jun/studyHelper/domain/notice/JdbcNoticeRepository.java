package jun.studyHelper.domain.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JdbcNoticeRepository implements NoticeRepository{

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void save(Notice notice) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Notice> findAll() {
        return null;
    }
}
