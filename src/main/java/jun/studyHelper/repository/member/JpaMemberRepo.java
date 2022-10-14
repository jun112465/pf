package jun.studyHelper.repository.member;

import jun.studyHelper.domain.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaMemberRepo extends JpaRepository<Member, Long> {
    @Override
    <S extends Member> S save(S entity);

    @Override
    Optional<Member> findById(Long id);

    @Override
    List<Member> findAll(Sort sort);


    /**
     * query methods
     * @param uid
     * @return
     */
    List<Member> findByUid(String uid);

}
