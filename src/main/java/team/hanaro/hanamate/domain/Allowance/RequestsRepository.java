package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.Requests;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RequestsRepository extends JpaRepository<Requests, Long> {
    List<Requests> findAllByRequesterIdxAndAskAllowanceIsNull(Long aLong);

    List<Requests> findByRequesterIdx(Long aLong);

    List<Requests> findAllByRequesterIdxAndAskAllowanceIsNotNull(Long aLong);

    List<Requests> findAllByTargetIdxAndAskAllowanceIsNull(Long aLong);

    List<Requests> findAllByTargetIdxAndAskAllowanceIsNotNull(Long aLong);

    List<Requests> findTop20ByRequesterIdxAndAskAllowanceIsNullOrderByCreateDateDesc(Long aLong);

    List<Requests> findTop20ByRequesterIdxAndAskAllowanceIsNotNullOrderByModifiedDateDesc(Long aLong);

    List<Requests> findTop20ByTargetIdxAndAskAllowanceIsNullOrderByCreateDateDesc(Long aLong);

    List<Requests> findTop20ByTargetIdxAndAskAllowanceIsNotNullOrderByModifiedDateDesc(Long aLong);

    Optional<Requests> findByRequestId(Long aLong);

    Long countAllByRequesterIdxAndAskAllowanceIsNull(Long aLong);
}
