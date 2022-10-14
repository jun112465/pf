package jun.studyHelper.repository.category;

import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Override
    List<Category> findAll();

    @Override
    <S extends Category> S save(S entity);

    @Override
    void deleteById(Long id);

    // Query
    List<Category> findByMemberId(Long memberId);
    List<Category> findByMember(Member member);
}
