package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
//    Optional<Users> findByEmail(String email);
    Optional<User> findByLoginId(String id);

//    boolean existsByEmail(String email);
    boolean existsByLoginId(String id);
}