package jun.studyHelper.service;

import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.UserDTO;
import jun.studyHelper.model.dto.PostDTO;
import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.repository.notice.PostRepository;
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

@Service
@Transactional
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<Post> add(PostDTO postDTO){

        return Optional.ofNullable(postRepository.save(Post.builder()
                .user(userRepository.findById(postDTO.getUserId()).orElse(null))
                .category(categoryRepository.findById(postDTO.getCategoryId()).orElse(null))
                .content(postDTO.getContent())
                .date(postDTO.getDate())
                .build()));
    }

    public void delete(Long noticeId){
        postRepository.deleteById(noticeId);
    }

    public Post findNotice(PostDTO postDTO){
        return postRepository.findById(postDTO.getNoticeId()).orElse(null);
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

    public void deleteNoticeListByCategory(CategoryDTO categoryDTO){
        postRepository.deleteAllByCategory(
                Category.builder()
                    .id(categoryDTO.getId())
                    .build()
        );
    }

    public List<Post> findPostList(){
        return postRepository.findAll();
    }

    public List<Post> findMemberNoticeList(UserDTO userDTO){
        User user = userRepository.findById(userDTO.getId()).get();
        return postRepository.findByUserIdOrderByDateAsc(user.getId());
    }

    public Map<Category, List<Post>> getNoticeListGroupedByCategory(UserDTO userDTO){

        List<Category> noticeCategories = categoryRepository.findAllByUserId(userDTO.getId());
        Map<Category, List<Post>> noticeCategoryListMap = new HashMap<>();
        for(Category nc : noticeCategories){
            noticeCategoryListMap.put(nc, postRepository.findByCategoryOrderByDateAsc(nc));
        }

        return noticeCategoryListMap;
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
