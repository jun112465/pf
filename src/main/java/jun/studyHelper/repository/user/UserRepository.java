package jun.studyHelper.repository.user;

import jun.studyHelper.model.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Override
    <S extends User> S save(S entity);


    @Override
    List<User> findAll(Sort sort);

    @Override
    <S extends User> Optional<S> findOne(Example<S> example);


//    @Override
//    void flush();
}
