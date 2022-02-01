package jun.studyHelper.controller;

import jun.studyHelper.domain.member.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class TestController {
    @GetMapping("/profile-test")
    public String profileImage(){
        return "profileImage";
    }

    @ResponseBody
    @PostMapping("/profile-upload")
    public void uploadProfile(MemberForm form){

//        System.out.println(form.getFile());
    }
}
