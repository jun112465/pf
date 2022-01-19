package jun.studyHelper;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class AutoAppConfig {
    private final DataSource dataSource;

    public AutoAppConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
