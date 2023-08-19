package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Parent;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent,Long> {

    Optional<Parent> findByLoginId(String id);
}
