package team.hanaro.hanamate.domain.User.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.ParentAndChild;

import java.util.List;

public interface ParentAndChildRepository extends JpaRepository<ParentAndChild, Long> {
//    List<Long> findAllByParentLoginIdAndChildLoginId(String parentId, String childrenId);
    boolean existsByParentIdxAndChildIdx(Long parentIdx, Long childIdx);

    void deleteByParentIdxAndChildIdx(Long parentIdx, Long childIdx);

    @EntityGraph(attributePaths = "child")
    List<ParentAndChild> findAllChildByParentLoginId(String parentId);
    @EntityGraph(attributePaths = "parent")
    List<ParentAndChild> findAllParentByChildLoginId(String childId);
}
