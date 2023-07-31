package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.domain.User.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
}