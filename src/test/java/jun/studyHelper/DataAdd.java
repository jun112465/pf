package jun.studyHelper;


import jun.studyHelper.model.UserRole;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Comment;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.repository.CommentRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import jun.studyHelper.repository.post.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class DataAdd {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

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
                    .userId(createRandomString())
                    .password(createRandomString())
                    .roles(List.of(UserRole.USER.getLabel()))
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
        categoryRepository.findAll().forEach(category -> {
            for(int i=0; i<10; i++){
                postRepository.save(
                        Post.builder()
                                .category(category)
                                .content(createRandomString())
                                .user(category.getUser())
                                .date(Post.getCurrentDate())
                                .build()
                );
            }
        });
    }


    @Test
    public void addComments(){
        List<Post> posts = postRepository.findAll();
        List<User> users = userRepository.findAll();

        posts.forEach(p -> {

            for(int i=0; i<10; i++){
                int userIdx = new Random().nextInt(users.size());
                Comment comment = Comment.builder()
                        .user(users.get(userIdx))
                        .post(p)
                        .content(createRandomString())
//                        .date(new Date())
                        .build();

                commentRepository.save(comment);
            }

        });



    }
}
