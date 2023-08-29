package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void verifyTest(){

        //given
        MemberDTO memberDTO = MemberDTO.builder()
                .uid("testId")
                .pwd("testPw")
                .build();
        Member member = Member.builder()
                .uid(memberDTO.getUid())
                .pw(memberDTO.getPwd())
                .build();
        member = memberRepository.save(member);

        //when
        Optional<Member> optionalMember = memberRepository.findOptionalByUid(memberDTO.getUid());
        if(optionalMember.isPresent())
            memberDTO.setId(optionalMember.get().getId());

        //then
        Assertions.assertThat(memberDTO.getId()).isEqualTo(member.getId());



    }

    @Test
    void loginTest(){
        //given
        MemberDTO memberDTO = MemberDTO.builder()
                .id(1)
                .uid("testId")
                .pwd("testPw")
                .build();

        //when
        String token = UUID.randomUUID().toString();
        ValueOperations<String,String> vop = redisTemplate.opsForValue();
        vop.set(token, String.valueOf(memberDTO.getId()));

        //then
        Long value = Long.valueOf(vop.get(token));
        Assertions.assertThat(value).isEqualTo(memberDTO.getId());
    }
}
