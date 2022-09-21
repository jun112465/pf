package jun.studyHelper.serviceTest;

import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestNotice {
    @Autowired
    NoticeService ns;

    @Test
    @DisplayName("문서 작성 테스트")
    @Modifying(clearAutomatically = true)
    public void test0(){
        Notice n = new Notice();
        n.setContent("blahblah");
        n.setMemberId("testId");
        n = ns.add(n);
        int id = n.getId();


        Notice n1 = new Notice();
        n1.setId(id);
        n1.setContent("bb");
        n1.setMemberId("testId");
        ns.editNote(n1);

        n1 = ns.findNotice(id);
        Notice n2 = ns.findNotice(id);
        System.out.println("n1 : " + n1);
        System.out.println("n2 : " + n2);
    }
}
