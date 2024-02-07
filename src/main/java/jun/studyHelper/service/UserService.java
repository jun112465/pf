package jun.studyHelper.service;

import jun.studyHelper.model.UserRole;
import jun.studyHelper.model.dto.JwtToken;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.exception.ErrorCode;
import jun.studyHelper.exception.IdDuplicateException;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor // 자동 생성자 주입
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public JwtToken login(String userId, String password){

        System.out.println("userService start");

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        log.info(authenticationToken.toString());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info(authentication.toString());

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        log.info(token.toString());


        log.info("AccessToken : " + token.getAccessToken() + " , RefreshToken : " + token.getRefreshToken());
        return token;
    }

    public JwtToken signIn(String username, String password){
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    /**
     * DB에서 member를 갖고온다.
     *
     * @param userDTO
     * @return
     */
    public Optional<User> findUser(UserDto userDTO){
        return userRepository.findById(userDTO.getUserId());
    }
//
//
//
    /**
     * 회원가입을 진행하는 메서드.
     *
     * @param userDTO
     * @return
     */
    public Optional<User> join(UserDto userDTO) {
        if(isMemberBlank(userDTO))
            throw new NoSuchElementException();
        if(!validateDuplicateUser(userDTO))
            throw new IdDuplicateException("id duplicated", ErrorCode.ID_DUPLICATION);

        return Optional.ofNullable(userRepository.save(User.builder()
                .userId(userDTO.getUserId())
                .password(userDTO.getPassword())
                .roles(List.of(UserRole.USER.getLabel()))
                .build()));
    }
//
//    public void deleteMember(UserDto userDTO){
//        User user;
//        if((user = findMember(userDTO).orElse(null)) != null)
//            userRepository.delete(user);
//    }
//
    /**
     * Uid를 갖고있는 member 가 이미 있는지 검증하는 메소드
     * 멤버가 없으면 -> true 반환
     * 멤버가 있으면 -> false 반환
     *
     * @param userDto
     * @return
     */
    private boolean validateDuplicateUser(UserDto userDto) {
        return userRepository.findById(userDto.getUserId()).isEmpty();
    }
//
//    public void deleteMember(User user){
//        userRepository.delete(user);
//    }
//
//
    /**
     * 회원가입 과정에서 폼으로 빈 데이터가 들어오는 것을 방지하는 코드다
     * uid or pw 가 비어있으면 true 반환
     * 둘 다 값이 있으면 false 반환
     * @param userDTO
     * @return
     */
    public boolean isMemberBlank(UserDto userDTO){
        if (userDTO.getUserId().isBlank() || userDTO.getPassword().isBlank()){
            return true;
        }
        return false;
    }
//
//
    /**
     *
     * 매개변수로 들어온 member 와 find 된 member의 일치 여부를 확인해준다.
     *
     * 찾지못하거나 uid or pw 가 일치하지 않으면 false 반환
     * uid 와 pw 가 일치하면 true 반환
     *
     * @param userDto
     * @return
     */
    public boolean validateMemberInfo(UserDto userDto){
        Optional<User> find = userRepository.findById(userDto.getUserId());
        if(find.isEmpty())
            return false;
        else if(userDto.getPassword().equals(find.get().getPassword()))
            return true;
        else
            return false;
    }
//
//
//    public List<User> findMembers(){
//        return userRepository.findAll();
//    }
//

    public void updateUser(UserDto userDto, UserDto updatedUserDto){
        log.info(updatedUserDto);
        log.info(userDto);
        User user = findUser(userDto).orElse(null);
        log.info("founded user : " + user);
        if(user != null){
            user.setPassword(updatedUserDto.getPassword());
        }else log.info("No User Found");
    }

    public void deleteUser(UserDto resignUserDto){
        if(validateMemberInfo(resignUserDto))
            userRepository.delete(findUser(resignUserDto).get());
    }


}


