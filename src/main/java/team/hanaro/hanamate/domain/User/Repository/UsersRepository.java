package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
//    Optional<Users> findByEmail(String email);
    Optional<Users> findById(String id);

//    boolean existsByEmail(String email);
    boolean existsById(String id);
}