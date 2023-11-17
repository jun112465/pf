package jun.studyHelper;


import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.notice.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootTest
public class DataAdd {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PostRepository postRepository;

    public String createRandomString(){
        Random rnd =new Random();
        StringBuffer buf =new StringBuffer();
        int stringLength = 10;

        for(int i=0;i<stringLength;i++) {
            if (rnd.nextBoolean()) buf.append((char)('a' + rnd.nextInt(26)));
            else buf.append(rnd.nextInt(10));
        }

        return buf.toString();
    }

    @Test
    void addUsers(){
        for(int i=0; i<10; i++){
            User user = User.builder()
                    .uid(createRandomString())
                    .pw(createRandomString())
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void addDefaultCategory(){
        userRepository.findAll().stream().forEach(user -> {
            System.out.println(user);
            categoryRepository.save(
                    Category.builder()
                        .name("default")
                        .user(user)
                        .build()
            );
        });

    }

    @Test
    void addRandomPosts(){
        userRepository.findAll().forEach(user -> {
            Category userCategory = categoryRepository.findAllByUserId(user.getId()).get(0);

            for(int i=0; i<10; i++) {
                postRepository.save(
                        Post.builder()
                                .category(userCategory)
                                .content(createRandomString())
                                .user(user)
                                .date(Post.getCurrentDate())
                                .build()
                );
            }
        });
    }


}
