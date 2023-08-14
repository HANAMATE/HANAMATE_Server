package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.hanaro.hanamate.entities.Requests;

import java.util.List;
import java.util.Optional;

public interface RequestsRepository extends JpaRepository<Requests, Long> {
    Optional<List<Requests>> findAllByRequesterId(Long aLong);

    Optional<Requests> findByRequestId(Long aLong);

    @Modifying
    @Query("update Requests r set r.askAllowance=:askAllowance where r.requestId=:requestId")
    int updateByRequestId(Long requestId, Boolean askAllowance);
}
