package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Child;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child,Long> {

    Optional<Child> findByLoginId(String id);
}
