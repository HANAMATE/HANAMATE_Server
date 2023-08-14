package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdx(Long aLong);

    //    Optional<Users> findByEmail(String email);
    Optional<User> findById(String id);

    //    boolean existsByEmail(String email);
    boolean existsById(String id);
}
