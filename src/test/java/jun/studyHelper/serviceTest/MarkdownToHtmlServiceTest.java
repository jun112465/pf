package jun.studyHelper.serviceTest;

import jun.studyHelper.model.entity.Post;
import jun.studyHelper.service.PostService;
import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class MarkdownToHtmlServiceTest {

    @Autowired
    PostService postService;

    @Test
    @DisplayName("Parser Test")
    void test1(){
        // given
        String md = "# Hello World \n - hello world";

        // when
        Parser parser = Parser.builder().build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String ret = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

        // then
        System.out.println(ret);
    }

    @Test
    @DisplayName("Get postList and parse to Html")
    void test2(){
        //given
        List<Post> posts = postService.findPostList();

        //when
        Parser parser = Parser.builder().build();

        posts.stream().forEach(post -> {
            Node document = parser.parse(post.getContent());
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            post.setContent(renderer.render(document));
        });

        //then
        posts.stream().forEach(p -> System.out.println(p.getContent()));
    }
}
