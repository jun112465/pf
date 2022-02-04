package jun.studyHelper.domain.member;

import jun.studyHelper.domain.notice.NoticeRepository;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Member {

    private int memberId;

    private String name;
    private String profileImage;
    private String profileMessage;

//    public NoticeService noticeService;



//    public Member(NoticeService noticeService){
//        this.noticeService = noticeService;
//    }
    public Member(){ }
    public Member(int memberId){
        this.memberId = memberId;
    }


    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileMessage() {
        return profileMessage;
    }

    public void setProfileMessage(String profileMessage) {
        this.profileMessage = profileMessage;
    }
}
