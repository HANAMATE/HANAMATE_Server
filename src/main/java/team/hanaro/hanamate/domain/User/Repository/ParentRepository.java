package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.hanaro.hanamate.entities.Child;
import team.hanaro.hanamate.entities.Parent;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("SELECT DISTINCT p FROM Parent p JOIN FETCH p.myChildList WHERE p.loginId = :loginId")
    Optional<Parent> fetchMyChildListByLoginId(@Param("loginId") String loginId);

    Optional<Parent> findByLoginId(String id);
}
