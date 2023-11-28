package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.PostDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.post.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    public final MarkdownToHtmlService markdownToHtmlService;

    @Autowired
    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            MarkdownToHtmlService markdownToHtmlService){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.markdownToHtmlService = markdownToHtmlService;
    }

    public Optional<Post> add(PostDto postDTO){

        return Optional.ofNullable(postRepository.save(Post.builder()
                .user(userRepository.findById(postDTO.getUserId()).orElse(null))
                .category(categoryRepository.findById(postDTO.getCategoryId()).orElse(null))
                .content(postDTO.getContent())
                .date(postDTO.getDate())
                .build()));
    }

    public List<Post> getPostsByCategory(CategoryDto categoryDTO){
        Category category = categoryRepository.getById(categoryDTO.getId());
        return postRepository.findByCategory(category);
    }

    public void delete(Long noticeId){
        postRepository.deleteById(noticeId);
    }

    public Post findNotice(PostDto postDTO){
        return postRepository.findById(postDTO.getId()).orElse(null);
    }


    @Modifying
    @Transactional
    public void editNote(Post post){
        Post tmp = postRepository.findById(post.getId()).get();
        tmp.setContent(post.getContent());
//        postRepository.update(post);
    }

    @Modifying
    @Transactional
    public void updateCategories(Category noticeCategory){
        Category nc = categoryRepository.findById(noticeCategory.getId()).get();
        nc.setName(noticeCategory.getName());
    }

    //쿼리

    public void deleteNoticeListByCategory(CategoryDto categoryDTO){
        postRepository.deleteAllByCategory(
                Category.builder()
                    .id(categoryDTO.getId())
                    .build()
        );
    }

    public List<PostDto> convertPostListToDTO(List<Post> postList){
        return postList.stream()
                .map(post -> {
                    PostDto dto = PostDto.builder()
                            .id(post.getId())
                            .userId(post.getUser().getUserId())
                            .categoryId(post.getCategory().getId())
                            .content(post.getContent())
                            .html(markdownToHtmlService.parseString(post.getContent()))
//                            .date(post.getDate())
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<Post> findPostList(){
        return postRepository.findAll();
    }


    public List<Post> findUserPostList(UserDto userDto){
        User user = userRepository.findById(userDto.getUserId()).get();
        return postRepository.findByUserOrderByDateAsc(user);
    }

    public Map<Category, List<Post>> getNoticeListGroupedByCategory(UserDto userDto){

        User user = userRepository.findById(userDto.getUserId()).get();
        List<Category> noticeCategories = categoryRepository.findAllByUser(user);
        Map<Category, List<Post>> noticeCategoryListMap = new HashMap<>();
        for(Category nc : noticeCategories){
            noticeCategoryListMap.put(nc, postRepository.findByCategoryOrderByDateAsc(nc));
        }

        return noticeCategoryListMap;
    }

    public boolean validateDeletePostUser(User user, Long deletePostId){
        return postRepository.getById(deletePostId).getUser().equals(user);
    }


    /**
     *
     * @param post
     * @return
     * 노트가 이미 추가됨 : true 반환
     * 노트가 아직 안추가됨 : false 반환
     *
     * 하루에 노트는 하나만 추가가 가능하다
     * 이미 추가된 노트가 있는지 확인해주는 메서드다.
     */
    public boolean isTodayNoticeAdded(Post post){
        List<Post> postList = postRepository.findByCategoryOrderByDateAsc(post.getCategory());
        if (postList.size() > 0) {
            String recentNoteDate = String.valueOf(postList.get(0).getDate());
            return post.getDate().equals(recentNoteDate);
        }

        return false;
    }
}
