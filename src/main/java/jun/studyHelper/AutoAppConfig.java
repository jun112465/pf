package jun.studyHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    public DataSource getDataSource() {
        return dataSource;
    }

    @Bean
    public DBconfig dBconfig(){
        return new DBconfig(dataSource);
    }
}
