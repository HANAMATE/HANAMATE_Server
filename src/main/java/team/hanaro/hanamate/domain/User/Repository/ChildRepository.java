package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.hanaro.hanamate.entities.Child;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child,Long> {
    @Query("SELECT DISTINCT c FROM Child c JOIN FETCH c.myParentList WHERE c.loginId = :loginId")
    Optional<Child> fetchMyParentListByLoginId(@Param("loginId") String loginId);
    Optional<Child> findByLoginId(String id);
}
