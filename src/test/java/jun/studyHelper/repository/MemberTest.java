package jun.studyHelper.repository;


import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.repository.member.JpaMemberRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Transactional
@SpringBootTest
public class MemberTest {

    @Autowired
    JpaMemberRepo repo;

    @Test
    @DisplayName("회원 추가 테스트")
    public void add(){

        //given
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String id = new String(array, StandardCharsets.UTF_8);
        new Random().nextBytes(array);
        String pw = new String(array, StandardCharsets.UTF_8);

        Member member = new Member();
        member.setUid(id);
        member.setPw(pw);

        //when
        repo.saveAndFlush(member);

        //then
        Assertions.assertThat(repo.findByUid(id).get(0)).isEqualTo(member);
    }

//    @Test
//    @DisplayName("회원 존재 여부 테스트")
//    public void check(){
//        //given
//        Member member = new Member("testid", "testpw");
//        Member member1 = new Member("testid", "testpw");
//        //when
//        memberRepository.save(member);
//
//        try{
//            memberRepository.save(member1);
//        }catch(Exception e){
//            System.err.println(e);
//        }
//    }

}