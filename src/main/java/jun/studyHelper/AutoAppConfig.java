package jun.studyHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@ComponentScan
public class AutoAppConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    @Autowired
    public AutoAppConfig(DataSource dataSource, EntityManager em){
        this.dataSource = dataSource;
        this.em = em;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Bean
    public DBconfig dBconfig(){
        return new DBconfig(dataSource);
    }
}
