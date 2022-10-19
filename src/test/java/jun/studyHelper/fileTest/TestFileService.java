package jun.studyHelper.fileTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlunit.builder.Input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@SpringBootTest
public class TestFileService {
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
}
