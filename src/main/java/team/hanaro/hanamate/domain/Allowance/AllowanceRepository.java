package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Requests;

import java.util.List;
import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<Requests, Long> {
    Optional<List<Requests>> findAllByRequesterId(Long aLong);
}
