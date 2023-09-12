package jun.studyHelper.service;

import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.exception.ErrorCode;
import jun.studyHelper.exception.IdDuplicateException;
import jun.studyHelper.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    public UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * DB에서 member를 갖고온다.
     *
     * @param userDTO
     * @return
     */
    public Optional<User> findMember(UserDTO userDTO){


        if(userDTO.getId()!=0)
            return userRepository.findById(userDTO.getId());


        List<User> userList = userRepository.findByUid(userDTO.getUid());
        if(userList.isEmpty()) return Optional.ofNullable(null);
        else return Optional.ofNullable(userList.get(0));
    }



    /**
     * 회원가입을 진행하는 메서드.
     *
     * @param userDTO
     * @return
     */
    public Optional<User> join(UserDTO userDTO) {
        if(isMemberBlank(userDTO))
            throw new NoSuchElementException();
        if(!validateDuplicateMember(userDTO))
            throw new IdDuplicateException("id duplicated", ErrorCode.ID_DUPLICATION);

        return Optional.ofNullable(userRepository.save(User.builder()
                .uid(userDTO.getUid())
                .pw(userDTO.getPwd())
                .build()));
    }

    public void deleteMember(UserDTO userDTO){
        User user;
        if((user = findMember(userDTO).orElse(null)) != null)
            userRepository.delete(user);
    }

    /**
     * Uid를 갖고있는 member 가 이미 있는지 검증하는 메소드
     * 멤버가 없으면 -> true 반환
     * 멤버가 있으면 -> false 반환
     *
     * @param member
     * @return
     */
    private boolean validateDuplicateMember(UserDTO member) {
        return userRepository.findByUid(member.getUid()).isEmpty();
    }

    public void deleteMember(User user){
        userRepository.delete(user);
    }


    /**
     * 회원가입 과정에서 폼으로 빈 데이터가 들어오는 것을 방지하는 코드다
     * uid or pw 가 비어있으면 true 반환
     * 둘 다 값이 있으면 false 반환
     * @param userDTO
     * @return
     */
    public boolean isMemberBlank(UserDTO userDTO){
        if (userDTO.getUid().isBlank() || userDTO.getPwd().isBlank()){
            return true;
        }
        return false;
    }


    /**
     *
     * 매개변수로 들어온 member 와 find 된 member의 일치 여부를 확인해준다.
     *
     * 찾지못하거나 uid or pw 가 일치하지 않으면 false 반환
     * uid 와 pw 가 일치하면 true 반환
     *
     * @param member
     * @return
     */
    public boolean validateMemberInfo(UserDTO member){

        Optional<User> find = userRepository.findOptionalByUid(member.getUid());
        return find.isPresent();

        // 동일한 uid 를 가진 유저 있는가?
//        List<Member> find = memberRepository.findByUid(member.getUid());
//        if(find.isEmpty())
//            return false;

//        return find.get(0).equals(member);
    }


    public List<User> findMembers(){
        return userRepository.findAll();
    }

    public Optional<User> findMemberByUid(String uid) {
        return Optional.ofNullable(
                userRepository
                        .findByUid(uid)
                        .get(0)
        );
    }
}


