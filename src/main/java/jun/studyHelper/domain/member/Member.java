package jun.studyHelper.domain.member;

public class Member {

    private String memberId;
    private String password;

    private String name;
    private String profileImage;
    private String profileMessage;

//    public NoticeService noticeService;



//    public Member(NoticeService noticeService){
//        this.noticeService = noticeService;
//    }
    public Member(){

    }
    public Member(String memberId){
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberId() {
        return memberId;
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

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
