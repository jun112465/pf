package jun.studyHelper.repository.category;

import jun.studyHelper.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    Optional<Category> findById(Long aLong);




    @Override
    List<Category> findAll();

    @Override
    <S extends Category> S save(S entity);

    @Override
    void deleteById(Long id);

    // Query
    List<Category> findAllByUserId(Long memberId);
    Optional<Category> findCategoryByUserIdAndName(long memberId, String name);
}
