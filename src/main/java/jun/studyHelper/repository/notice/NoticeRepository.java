package jun.studyHelper.repository.notice;

import jun.studyHelper.model.entity.User;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.model.entity.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// https://jobc.tistory.com/120
public interface NoticeRepository extends JpaRepository<Notice, Long> {


    @Override
    <S extends Notice> S save(S entity);

    @Override
    void deleteAll(Iterable<? extends Notice> entities);

    @Override
    <S extends Notice> S saveAndFlush(S entity);

//    @Override
//    Notice getById(Long aLong);

    @Override
    Optional<Notice> findById(Long aLong);

    @Override
    <S extends Notice> List<S> findAll(Example<S> example);

    @Override
    void deleteById(Long id);

    // 쿼리 메소드 추가
    List<Notice> findByUserIdOrderByDateAsc(long userId);
    List<Notice> findByCategoryOrderByDateAsc(Category noticeCategory);
    void deleteAllByCategory(Category category);


    List<Notice> findByUser(User user);
}
