package jun.studyHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class StudyHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyHelperApplication.class, args);
	}

}
