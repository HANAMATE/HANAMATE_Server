package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.Requests;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RequestsRepository extends JpaRepository<Requests, Long> {
    Optional<List<Requests>> findAllByRequesterIdAndAskAllowanceIsNull(Long aLong);

    Optional<List<Requests>> findAllByRequesterIdAndAskAllowanceIsNotNull(Long aLong);

    Optional<List<Requests>> findAllByTargetIdAndAskAllowanceIsNull(Long aLong);

    Optional<List<Requests>> findAllByTargetIdAndAskAllowanceIsNotNull(Long aLong);

    Optional<Requests> findByRequestId(Long aLong);
}
