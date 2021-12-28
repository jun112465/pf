package jun.studyHelper;

import jun.studyHelper.domain.notice.MemoryNoticeRepository;
import jun.studyHelper.domain.notice.NoticeRepository;
import jun.studyHelper.service.NoticeService;

public class AppConfig {

    public NoticeService noticeService(){
        NoticeService ns = new NoticeService(noticeRepository());
        return ns;
    }

    public NoticeRepository noticeRepository(){ return new MemoryNoticeRepository(); }
}
