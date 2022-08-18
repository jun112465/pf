package jun.studyHelper.controller;

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
    public String rootController(
            Model model,
            @RequestParam(value = "error-msg", required = false)String errorMsg,
            @CookieValue(name="memberId", required = false)String memberId){


        try{
            Member member = null;
            // 로그인 상태
            if ((member=memberService.findOne(memberId)) != null){
                model.addAttribute("noticeList", noticeService.findMemberNoticeList(member));
                model.addAttribute("groupList", groupService.getMemberGroups(member));
            }
            // 로그아웃 상태
            else{

            }
            // 공통 모델
            model.addAttribute("errorMsg", errorMsg);
            model.addAttribute("user", memberService.findOne(memberId));
        } catch(NullPointerException e){
            throw e;
        } catch(Exception e){
            throw e;
        }
//        try{
//            System.out.println("MainController : memberId = " + memberId);
////            model.addAttribute("user", memberService.findOne(Integer.parseInt(memberId)));
//            model.addAttribute("memberId", memberId);
//            model.addAttribute("noticeList", noticeService.findNoticeList(Integer.parseInt(memberId)));
////            model.addAttribute("friendList", memberService.getFriends(new Member(Integer.parseInt(memberId))));
//        } catch (NullPointerException | NumberFormatException e) {
//            e.printStackTrace();
//        }
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



