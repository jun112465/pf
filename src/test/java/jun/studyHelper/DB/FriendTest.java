package jun.studyHelper.DB;

import jun.studyHelper.domain.member.FriendRepository;
import jun.studyHelper.domain.member.JdbcFriendRepository;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.service.FriendService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FriendTest {

    @Autowired
    FriendService fs;

    @Test
    public void 친구추가(){
        //given
        Member me = new Member("testId");
        Member friend = new Member("testFriendId");

        //when
        fs.addFriend(me, friend);

        //then
        System.out.println(fs.getFriendList(me));
    }

}
