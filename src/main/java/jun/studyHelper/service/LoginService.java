package jun.studyHelper.service;

import jun.studyHelper.controller.LoginController;
import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    RedisTemplate<String,String> redisTemplate;
    MemberRepository memberRepository;
}
