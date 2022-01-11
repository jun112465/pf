package jun.studyHelper.controller;

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

    @Value("${fileRoot}")
    String fileRoot;

    @Autowired
    MainController(MemberService memberService, NoticeService noticeService){
        this.memberService = memberService;
        this.noticeService = noticeService;
    }
    @GetMapping("/")
    public String rootController(Model model, @CookieValue(name="memberId", required = false)String memberId){
        try{
            System.out.println("MainController : memberId = " + memberId);
            model.addAttribute("user", memberService.findOne(Integer.valueOf(memberId)));
            model.addAttribute("memberId", memberId);
            model.addAttribute("noticeList", noticeService.findNoticeList(Integer.valueOf(memberId)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        return "index";
    }

    @GetMapping("/page")
    public String myPageController(Model model, @CookieValue(name="memberId", required = false)String memberId){

        try{
            model.addAttribute("user", memberService.findOne(Integer.valueOf(memberId)));
            model.addAttribute("memberId", memberId);
            model.addAttribute("noticeList", noticeService.findNoticeList(Integer.valueOf(memberId)));
        }catch(Exception e){
            e.printStackTrace();
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
}



