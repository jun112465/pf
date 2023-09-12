package jun.studyHelper.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import jun.studyHelper.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class FileService {
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    public FileService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public void uploadToS3(MultipartFile multipartFile, String path) throws IOException {

        long size = multipartFile.getSize();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(size);

        // s3 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(
                        BUCKET, path, multipartFile.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }

    public MultipartFile downloadFromS3(String key){
        System.out.println("start");

        S3Object s3Object = amazonS3Client.getObject(
                new GetObjectRequest(BUCKET, key)
        );

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        try {
            byte[] bytes = IOUtils.toByteArray(objectInputStream);
            File myFile = new File("/images/" + key);
            OutputStream os = new FileOutputStream(myFile);
            os.write(bytes);
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return (MultipartFile) s3Object.getObjectContent();
    }

    public byte[] getProfileImg(User user) throws MalformedURLException {
        String fileName = String.valueOf(user.getId());
        URL url = new URL("https://integrated-bucket.s3-ap-northeast-2.amazonaws.com/profiles/" + fileName);

        HttpURLConnection conn = null;
        InputStream inputStream = null;

        try {
            // 프로필 이미지가 있으면 해당 이미지 return
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inputStream = conn.getInputStream();

            return inputStream.readAllBytes();
        } catch (FileNotFoundException e){

            // 프로필 이미지가 따로 없으므로 default 이미지 return
            url = new URL("https://integrated-bucket.s3-ap-northeast-2.amazonaws.com/profile");
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                inputStream = conn.getInputStream();
                return inputStream.readAllBytes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
