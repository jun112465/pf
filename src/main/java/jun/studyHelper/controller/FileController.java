package jun.studyHelper.controller;


import com.amazonaws.services.s3.AmazonS3Client;
import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class FileController {

    FileService fileService;
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;


    @Autowired
    public FileController(FileService fileService, AmazonS3Client amazonS3Client) {
        this.fileService = fileService;
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
        String newFileName = PATH + loginMember.getId() + "." + multipartFile.getContentType();

        // s3 업로드
        fileService.uploadToS3(multipartFile, newFileName);
    }

    @GetMapping(value = "/file/get-profile", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public byte[] getProfile(HttpServletRequest req) throws MalformedURLException {
        Member loginMember = (Member)req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);


        byte[] img = fileService.getProfileImg(loginMember);
        ByteArrayInputStream bis = new ByteArrayInputStream(img);
        BufferedImage image = null;
        try {
            image = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //png to jpg
        BufferedImage toJpg = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        toJpg.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        image = toJpg;

        // create the object of ByteArrayOutputStream class
        ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();

        // write the image into the object of ByteArrayOutputStream class
        try {
            ImageIO.write(image, "jpg", outStreamObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // create the byte array from image
        byte [] byteArray = outStreamObj.toByteArray();
        return byteArray;
//        return fileService.downloadFromS3(String.valueOf(loginMember.getId()));
//        return fileService.getProfileImg(loginMember);
    }

    @GetMapping(value = "file/get-profile-test", produces = MediaType.ALL_VALUE)
    public @ResponseBody byte[] getProfileTest(HttpServletResponse resp) throws IOException {

        URL url = new URL("https://integrated-bucket.s3-ap-northeast-2.amazonaws.com/profile");


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();

        return inStream.readAllBytes();
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//
//        byte[] buffer = new byte[10240];
//        int len = 0;
//        while ((len = inStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, len);
//        }
//        inStream.close();
//
//        byte[] btImg = outStream.toByteArray();

//        return btImg;
    }
}
