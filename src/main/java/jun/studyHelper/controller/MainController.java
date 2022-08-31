package jun.studyHelper.controller;

import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.member.Member;
import jun.studyHelper.service.GroupService;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class MainController {

    MemberService memberService;
    NoticeService noticeService;
    GroupService groupService;


    @Value("${fileRoot}")
    String fileRoot;


    @Autowired
    MainController(MemberService memberService, NoticeService noticeService, GroupService groupService){
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String rootController(Model model, HttpServletRequest req){

        HttpSession session = req.getSession(false);
        if (session != null) {
            // add model attributes
            Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("member", loginMember);
            model.addAttribute("noticeList", noticeService.findMemberNoticeList(loginMember));
//            System.out.println(loginMember);
//
//            model.addAttribute("noticeList", noticeService.findMemberNoticeList(loginMember));
        }else{
            model.addAttribute("member", null);
        }

        return "index";
    }


    @GetMapping("/image-uploader")
    public String imageController(){
        return "imageUploader";
    }


    @PostMapping(value="/upload", produces = "application/json")
    @ResponseBody
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        JSONObject jsonObject = new JSONObject();

        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        assert originalFileName != null;
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            System.out.println("original file name : " + originalFileName);
            System.out.println("saved file name : " + savedFileName);
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);

            jsonObject.put("url", "/getFiles/"+savedFileName);
            jsonObject.put("responseCode", "success");
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @ResponseBody
    @GetMapping
    public String viewFriendSchedule(){

        return "/";
    }
}



