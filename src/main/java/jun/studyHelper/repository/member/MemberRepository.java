package jun.studyHelper.repository.member;

import jun.studyHelper.model.entity.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    <S extends Member> S save(S entity);

    @Override
    Optional<Member> findById(Long id);

    @Override
    List<Member> findAll(Sort sort);

    @Override
    <S extends Member> Optional<S> findOne(Example<S> example);

    /**
     * query methods
     * @param uid
     * @return
     */
    List<Member> findByUid(String uid);

    Optional<Member> findOptionalByUid(String uid);

//    @Override
//    void flush();
}
