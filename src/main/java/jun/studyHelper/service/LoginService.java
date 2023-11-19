package jun.studyHelper.service;

import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {

//    UserRepository userRepository;
//
//    @Autowired
//    public LoginService(
//            UserRepository userRepository,
//            RedisTemplate<String,String> redisTemplate
//    ){
//        this.userRepository = userRepository;
//    }
//
//    public String login(UserDto userDTO){
//
//        String token;
//
//        userDTO = assignMemberId(userDTO);
//
//        // 토큰 생성
//        token = UUID.randomUUID().toString();
//
//        // 세션 할당
//
//        // return
//        return token;
//    }
//
//    public UserDto getUserDTO(String sessionId){
//        return null;
//    }
//
//    public boolean isUserLoggedIn(String sessionId){
//        //User 로그인 된 상태면 true 반환
//        //반대의 경우 false 반환
//        return true;
//    }
//
//    public UserDto assignMemberId(UserDto userDTO){
//        Optional<User> optionalMember = userRepository.findOptionalByUid(userDTO.getUid());
//        if(optionalMember.isPresent())
//            userDTO.setId(optionalMember.get().getId());
//        else
//            userDTO = null;
//
//        return userDTO;
//    }
//
//    public void logout(String token){
//
//    }
}
