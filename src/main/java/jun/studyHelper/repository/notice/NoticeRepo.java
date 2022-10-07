package jun.studyHelper.repository.notice;

import jun.studyHelper.entity.Notice;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// https://jobc.tistory.com/120
public interface NoticeRepo extends JpaRepository<Notice, Integer> {
    @Override
    default <S extends Notice> S save(S entity) {
        return null;
    }

    @Override
    Optional<Notice> findById(Integer integer);

    @Override
    <S extends Notice> List<S> findAll(Example<S> example);

    @Override
    void deleteById(Integer integer);

    // 쿼리 메소드 추가
    List<Notice> findByMemberId(String memberId);
    List<Notice> findByCategoryId(Integer categoryId);
}
