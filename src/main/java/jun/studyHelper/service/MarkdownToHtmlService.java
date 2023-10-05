package jun.studyHelper.service;

import jun.studyHelper.model.entity.Post;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkdownToHtmlService {

    public List<Post> parsePostListToHtml(List<Post> posts){
        Parser parser = Parser.builder().build();

        posts.stream().forEach(post -> {
            Node document = parser.parse(post.getContent());
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            post.setContent(renderer.render(document));
        });

        return posts;
    }

    public Post parsePostToHtml(Post post){
        Parser parser = Parser.builder().build();

        Node document = parser.parse(post.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        post.setContent(renderer.render(document));

        return post;
    }

    public String parseString(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
