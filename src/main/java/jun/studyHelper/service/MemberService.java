package jun.studyHelper.service;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.repository.member.MemberRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MemberService {
    public  MemberRepository memberRepository;

//    @Value("${fileRoot}")
//    String fileRoot;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    /**
     * 회원가입
     */
    public boolean join(Member member) {
        // 성공 -> true 반환
        // 실패 -> false 반환
        if (!validateDuplicateMember(member)){
            return false;
        }; //중복 회원 검증
        memberRepository.save(member);
        return true;
    }
    private boolean validateDuplicateMember(Member member) {
        if(memberRepository.findById(member.getId()).isPresent())
            return false;
        return true;
    }

    public boolean isMemberNull(Member member){
        if (member.getId().isBlank() || member.getPw().isBlank()){
            return false;
        }
        return true;
    }


    public Member validateMemberInfo(Member member){
        Optional<Member> find = findOne(member.getId());
        return find.orElse(null);
    }



    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String memberId) {
        return memberRepository.findById(memberId);
    }

    public void addFriend(Member me, Member friend){
//        memberRepository.addFriend(me,friend);
    }
    public Map<Integer, String> getFriends(Member me){
        Map<Integer, String> list = memberRepository.getFriends(me);
        return list;
    }

//    public Map<Integer, String> deleteFriend(String memberId, int friendId){
//        memberRepository.deleteFriend(memberId, friendId);
//        return memberRepository.getFriends(new Member(memberId));
//    }

//    public void updateMemberInfo(MultipartFile profileImage, String profileMessage, int memberId){
//        String fileName = null;
//        if(!profileImage.isEmpty())
//            fileName = saveFileToServer(profileImage);
//        memberRepository.updateMemberInfo(fileName, profileMessage, memberId);
//    }

//    public String saveFileToServer(MultipartFile profileImage){
//        String originalFileName = profileImage.getOriginalFilename();	//오리지날 파일명
//        assert originalFileName != null;
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
//
//        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
//
//        File targetFile = new File(fileRoot + savedFileName);
//        InputStream fileStream = null;
//        try {
//            fileStream = profileImage.getInputStream();
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return savedFileName;
//    }
}


