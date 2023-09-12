package jun.studyHelper.serviceTest;

import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
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
    UserRepository userRepository;

    @Test
    void verifyTest(){

        //given
        UserDTO userDTO = UserDTO.builder()
                .uid("testId")
                .pwd("testPw")
                .build();
        User user = User.builder()
                .uid(userDTO.getUid())
                .pw(userDTO.getPwd())
                .build();
        user = userRepository.save(user);

        //when
        Optional<User> optionalMember = userRepository.findOptionalByUid(userDTO.getUid());
        if(optionalMember.isPresent())
            userDTO.setId(optionalMember.get().getId());

        //then
        Assertions.assertThat(userDTO.getId()).isEqualTo(user.getId());



    }

    @Test
    void loginTest(){
        //given
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .uid("testId")
                .pwd("testPw")
                .build();

        //when
        String token = UUID.randomUUID().toString();
        ValueOperations<String,String> vop = redisTemplate.opsForValue();
        vop.set(token, String.valueOf(userDTO.getId()));

        //then
        Long value = Long.valueOf(vop.get(token));
        Assertions.assertThat(value).isEqualTo(userDTO.getId());
    }
}
