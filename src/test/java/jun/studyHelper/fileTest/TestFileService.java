package jun.studyHelper.fileTest;

import jun.studyHelper.service.FileService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xmlunit.builder.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

@SpringBootTest
public class TestFileService {

    @Autowired
    FileService fileService;

    @Test
    @DisplayName("프로필 사진 byte 변환")
    public void getProfile1() throws MalformedURLException {

        URL url = new URL("https://integrated-bucket.s3-ap-northeast-2.amazonaws.com/profile");
        HttpURLConnection conn;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);

            inputStream = conn.getInputStream();
            inputStream.close();
        } catch (FileNotFoundException e){
            System.out.println("file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Assertions.assertThat(inputStream).isNotNull();
        }
    }

    @Test
    @DisplayName("존재하지 않는 프로필 사진 예외 처리")
    public void getProfile2() throws MalformedURLException {

        // 없는 파일 가져오기
        String rdm = UUID.randomUUID().toString();
        URL url = new URL("https://integrated-bucket.s3-ap-northeast-2.amazonaws.com/" + rdm);

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inputStream = conn.getInputStream();
        } catch (FileNotFoundException e){
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Assertions.assertThat(inputStream).isNull();
        }
    }


    @Test
    @DisplayName("파일 등록하기")
    public void uploadImageFileToS3() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/images/laptop.jpeg");
        MultipartFile multipartFile = new MockMultipartFile("2", new FileInputStream(resource.getFile()));

        // 모든 사람이 읽을 수 있는 권한으로 파일이 s3에 등록된다.
        fileService.uploadToS3(multipartFile, "profiles/"+"2");

    }
}
