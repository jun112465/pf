package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDto;
import jun.studyHelper.model.dto.PageInfo;
import jun.studyHelper.model.dto.PostDto;
import jun.studyHelper.model.dto.UserDto;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.post.PostRepository;
import jun.studyHelper.repository.user.UserRepository;
import jun.studyHelper.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    public final MarkdownToHtmlService markdownToHtmlService;

    private static final Integer pageSize = 12;

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
                            .comments(post.getCommentDtoList())
                            .date(post.getDate())
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<Post> findPostList(){
        postRepository.findAll();

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

    public List<PostDto> getPostPage(int pageNo){
        PageRequest pageRequest = PageRequest.of(pageNo-1, pageSize, Sort.by("date").descending());
        List<Post> posts = postRepository.findAll(pageRequest).getContent();
        return convertPostListToDTO(posts);
    }

    public List<PostDto> getPostPage(int pageNo, long categoryId){
        //overloading
        Category targetCategory = categoryRepository.findById(categoryId).get();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by("date").descending());
        List<Post> posts = postRepository.findByCategory(targetCategory, pageable);
        return convertPostListToDTO(posts);
    }
    public int getTotalPage(){
        return (int) Math.ceil((double)postRepository.findAll().size() / pageSize);
    }
    public int getTotalPage(long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        return (int) Math.ceil((double)postRepository.findByCategory(category.orElse(null)).size() / pageSize);
    }
    public PageInfo getPageRange(int pageNo){
        int lastPageNo = Math.max((int) Math.ceil((double)postRepository.findAll().size() / pageSize), 1);
        return PageInfo.builder()
                .pageNo(pageNo)
                .firstPageNo(1)
                .prevPageNo(Math.max(1, pageNo-1))
                .lastPageNo(lastPageNo)
                .nextPageNo(Math.min(lastPageNo, pageNo+1))
                .start((pageNo-1)/10*10+1)
                .end(Math.min(lastPageNo, (pageNo + 9) / 10 * 10))
                .build();
    }
    public PageInfo getPageRange(int pageNo, long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        int lastPageNo = Math.max((int) Math.ceil((double)postRepository.findByCategory(category.orElse(null)).size() / pageSize), 1);
        return PageInfo.builder()
                .pageNo(pageNo)
                .firstPageNo(1)
                .prevPageNo(Math.max(1, pageNo-1))
                .lastPageNo(lastPageNo)
                .nextPageNo(Math.min(lastPageNo, pageNo+1))
                .start((pageNo-1)/10*10+1)
                .end(Math.min(lastPageNo, (pageNo + 9) / 10 * 10))
                .build();
    }
}
