package jun.studyHelper.repositoryTest;


import jun.studyHelper.model.entity.Member;
import jun.studyHelper.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

@Transactional
@SpringBootTest
//@DataJpaTest
public class MemberRepoTest {

    @Autowired
    MemberRepository repo;

    @Test
    @DisplayName("회원 추가 테스트")
    public void add(){

        //given
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String tmpUid = new String(array, StandardCharsets.UTF_8);
        new Random().nextBytes(array);
        String tmpPw = new String(array, StandardCharsets.UTF_8);

        repo.save(Member.builder()
                .uid(tmpUid)
                .pw(tmpPw)
                .build());

        //when
        List<Member> memberList = repo.findAll();
        Member tmpMember = memberList.get(0);

        //then
        Assertions.assertThat(tmpMember.getUid()).isEqualTo(tmpUid);
        Assertions.assertThat(tmpMember.getPw()).isEqualTo(tmpPw);
    }

    @Test
    @DisplayName("회원 존재 여부 테스트")
    public void check(){
        //given
        Member member = new Member("testid", "testpw");
        //when
        repo.save(member);
        repo.flush();
//        memberRepository.save(member);

        Assertions.assertThat(repo.findByUid(member.getUid()).get(0)).isEqualTo(repo.findOptionalByUid(member.getUid()).get());
//        System.out.println(repo.findByUid(member.getUid()));
//        System.out.println(repo.findOptionalByUid(member.getUid()));
    }

}
