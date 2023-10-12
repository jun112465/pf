package jun.studyHelper.repository.notice;

import jun.studyHelper.model.entity.Post;
import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// https://jobc.tistory.com/120
public interface PostRepository extends JpaRepository<Post, Long> {


    @Override
    <S extends Post> S save(S entity);

    @Override
    void deleteAll(Iterable<? extends Post> entities);

    @Override
    <S extends Post> S saveAndFlush(S entity);

//    @Override
//    Post getById(Long aLong);

    @Override
    Optional<Post> findById(Long aLong);

    @Override
    <S extends Post> List<S> findAll(Example<S> example);

    @Override
    void deleteById(Long id);

    // 쿼리 메소드 추가
    List<Post> findByUserIdOrderByDateAsc(long userId);
    List<Post> findByCategoryOrderByDateAsc(Category noticeCategory);
    void deleteAllByCategory(Category category);


    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
