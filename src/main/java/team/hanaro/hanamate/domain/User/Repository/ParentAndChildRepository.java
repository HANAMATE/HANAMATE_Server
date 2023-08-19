package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.ParentAndChild;

import java.util.List;

public interface ParentAndChildRepository extends JpaRepository<ParentAndChild, Long> {
    List<Long> findAllByParentIdAndChildrenId(Long parentId, Long childrenId);
}
