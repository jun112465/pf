package jun.studyHelper.PostTest;

import jun.studyHelper.SpringConfig;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class NoticeAddTest {

    @Test
    public void testNoticeAdd(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        NoticeService noticeService = ac.getBean("noticeService", NoticeService.class);

        Notice n = new Notice();
        n.setMemberId(18011646);
        n.setContents("testing");
        noticeService.add(n);

        List<Notice> noticeList = noticeService.findNoticeList(18011646);
        for(Notice nt : noticeList){
            System.out.println("id : " + nt.getMemberId() + ", contents : " + nt.getContents());
        }
    }
}
