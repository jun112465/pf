package jun.studyHelper.repository.member;

import jun.studyHelper.domain.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaMemberRepo extends JpaRepository<Member, String> {
    @Override
    <S extends Member> S save(S entity);

    @Override
    Optional<Member> findById(String s);

    @Override
    List<Member> findAll(Sort sort);
}
