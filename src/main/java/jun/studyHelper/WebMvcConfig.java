package jun.studyHelper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Value("${fileRoot}")
    String fileRoot;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //배포용
//        registry.addResourceHandler("/serverImages/**")
//                .addResourceLocations("file:" + fileRoot);

        //로컬용
        registry.addResourceHandler("/getFiles/**")
                .addResourceLocations("file:" + fileRoot);
    }
}