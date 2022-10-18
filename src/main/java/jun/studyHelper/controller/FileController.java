package jun.studyHelper.controller;


import com.amazonaws.services.s3.AmazonS3Client;
import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileController {

    FileService fileService;
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;


    public FileController(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @PostMapping(value = "/file/update-profile", produces = "application/json")
    @ResponseBody
    public void uploadProfile(
            @RequestParam("profile") MultipartFile multipartFile,
            HttpServletRequest req
    ) throws IOException {

        String PATH = "profiles/";

        // 새로 이름 부여하기
        Member loginMember = (Member) req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        String newFileName = PATH + loginMember.getId();

        // s3 업로드
        fileService.uploadToS3(multipartFile, newFileName);
    }
}
