package jun.studyHelper.fileTest;

import org.junit.jupiter.api.Test;

import java.io.File;

public class ReadFile {


    @Test
    void read(){
        File file = new File("dog.jpeg");
        System.out.println(file.getAbsolutePath());
    }

}
