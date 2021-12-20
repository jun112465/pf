package jun.studyHelper;

import jun.studyHelper.domain.MemberRepository;
import jun.studyHelper.domain.MemoryMemberRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppConfig {

    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
