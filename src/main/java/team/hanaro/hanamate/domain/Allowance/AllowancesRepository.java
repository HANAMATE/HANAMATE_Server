package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.Allowances;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AllowancesRepository extends JpaRepository<Allowances, Long> {

    Optional<Allowances> findByAllowanceIdAndValidIsTrue(Long aLong);

    Optional<Allowances> findByParentIdxAndValidIsTrue(Long aLong);

    Optional<Allowances> findByChildrenIdxAndParentIdxAndValidIsTrue(Long childId, Long parentId);

    Optional<Allowances> findByChildrenIdxAndValidIsTrue(Long aLong);
}
