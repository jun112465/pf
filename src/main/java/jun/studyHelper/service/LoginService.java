package jun.studyHelper.service;

import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {

    RedisTemplate<String,String> redisTemplate;
    UserRepository userRepository;

    @Autowired
    public LoginService(
            UserRepository userRepository,
            RedisTemplate<String,String> redisTemplate
    ){
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public String login(UserDTO userDTO){
        ValueOperations<String,String> vop = redisTemplate.opsForValue();
        String token;


        userDTO = assignMemberId(userDTO);

        // 토큰 생성
        do{
            token = UUID.randomUUID().toString();
        }while(vop.get(token) != null);

        // 세션 할당
        vop.set(token, String.valueOf(userDTO.getId()));

        // return
        return token;
    }

    public UserDTO getUserDTO(String sessionId){
        ValueOperations<String,String> vop = redisTemplate.opsForValue();
        User user = userRepository.findById(Long.valueOf(vop.get(sessionId))).orElse(null);

        return UserDTO.builder()
                .id(user.getId())
                .uid(user.getUid())
                .pwd(user.getPw())
                .build();
    }

    public boolean isUserLoggedIn(String sessionId){
        //User 로그인 된 상태면 true 반환
        //반대의 경우 false 반환

        ValueOperations<String,String> vop = redisTemplate.opsForValue();
        if(vop.get(sessionId) == null)
            return false;
        else
            return true;
    }

    public UserDTO assignMemberId(UserDTO userDTO){
        Optional<User> optionalMember = userRepository.findOptionalByUid(userDTO.getUid());
        if(optionalMember.isPresent())
            userDTO.setId(optionalMember.get().getId());
        else
            userDTO = null;

        return userDTO;
    }
}
