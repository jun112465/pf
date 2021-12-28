package jun.studyHelper.PostTest;

import jun.studyHelper.AppConfigBean;
import jun.studyHelper.domain.notice.Notice;
import jun.studyHelper.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class NoticeAddTest {

    @Test
    public void testNoticeAdd(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigBean.class);
        NoticeService noticeService = ac.getBean("noticeService", NoticeService.class);

        Notice n = new Notice();
        n.setId(1);
        n.setContents("testing");
        noticeService.add(n);

        for(Notice nt : noticeService.findNoticeList()){
            System.out.println("id : " + nt.getId() + ", contents : " + nt.getContents());
        }
    }
}
