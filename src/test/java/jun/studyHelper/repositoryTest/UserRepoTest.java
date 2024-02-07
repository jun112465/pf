package jun.studyHelper.repositoryTest;


import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

@Transactional
@SpringBootTest
public class UserRepoTest {

    @Autowired
    UserRepository userRepository;
    /*
    @Autowired
    UserRepository repo;

    @Test
    @DisplayName("회원 추가 테스트")
    public void add(){

        //given
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String tmpUid = new String(array, StandardCharsets.UTF_8);
        new Random().nextBytes(array);
        String tmpPw = new String(array, StandardCharsets.UTF_8);

        repo.save(User.builder()
                .uid(tmpUid)
                .pw(tmpPw)
                .build());

        //when
        List<User> userList = repo.findAll();
        User tmpUser = userList.get(0);

        //then
        Assertions.assertThat(tmpUser.getUid()).isEqualTo(tmpUid);
        Assertions.assertThat(tmpUser.getPw()).isEqualTo(tmpPw);
    }

    @Test
    @DisplayName("회원 존재 여부 테스트")
    public void check(){
        //given
        User user = new User("testid", "testpw");
        //when
        repo.save(user);
        repo.flush();
//        memberRepository.save(member);

        Assertions.assertThat(repo.findByUid(user.getUid()).get(0)).isEqualTo(repo.findOptionalByUid(user.getUid()).get());
//        System.out.println(repo.findByUid(member.getUid()));
//        System.out.println(repo.findOptionalByUid(member.getUid()));
    }


     */

    @Test
    public void find(){
        System.out.println(userRepository.findById("0bw66770gc"));
    }
}
