package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.Allowances;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AllowancesRepository extends JpaRepository<Allowances, Long> {

    Optional<Allowances> findByAllowanceId(Long aLong);

    Optional<List<Allowances>> findAllByParentId(Long aLong);

    Optional<Allowances> findByChildrenIdAndParentId(Long childId, Long parentId);
}
