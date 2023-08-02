package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Requests;

import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<Requests, Long> {
    Optional<Requests> findAllByRequesterId(Long aLong);
}
