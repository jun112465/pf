package jun.studyHelper.repository.notice;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Notice;
import jun.studyHelper.domain.entity.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// https://jobc.tistory.com/120
public interface JpaNoticeRepo extends JpaRepository<Notice, Long> {


    @Override
    void deleteAll(Iterable<? extends Notice> entities);

    @Override
    <S extends Notice> S saveAndFlush(S entity);

    @Override
    Optional<Notice> findById(Long aLong);

    @Override
    <S extends Notice> List<S> findAll(Example<S> example);

    @Override
    void deleteById(Long id);

    // 쿼리 메소드 추가
    List<Notice> findByMemberIdOrderByDateAsc(long memberId);
    List<Notice> findByCategoryOrderByDateAsc(Category noticeCategory);
    void deleteAllByCategory(Category category);


    List<Notice> findByMember(Member member);
}
