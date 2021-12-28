package jun.studyHelper.domain.member;

import jun.studyHelper.domain.notice.NoticeRepository;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;

public class Member {
    private int id;
    private String name;
    public NoticeService noticeService;

    public Member(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
