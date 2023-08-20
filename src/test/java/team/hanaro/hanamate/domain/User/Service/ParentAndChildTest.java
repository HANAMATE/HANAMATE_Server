package team.hanaro.hanamate.domain.User.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.domain.User.Repository.ChildRepository;
import team.hanaro.hanamate.domain.User.Repository.ParentAndChildRepository;
import team.hanaro.hanamate.domain.User.Repository.ParentRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.entities.Child;
import team.hanaro.hanamate.entities.Parent;
import team.hanaro.hanamate.entities.ParentAndChild;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test") // "test" 프로파일 활성화
@Transactional
class ParentAndChildTest {
    @Autowired
    ParentRepository parentRepository;
    @Autowired

    ChildRepository childRepository;
    @Autowired
    ParentAndChildRepository parentAndChildRepository;

    @PersistenceContext
    EntityManager em;
    @Test
    void findByUserPhoneNum() {

    }

    @Test
    @Rollback(value = false)
    @DisplayName("부모-아이 연결 후 꺼내온 부모와 로그인아이디로 검색한 아이의 부모의 객체가 같아야 한다.")
    void 부모가_아이_추가하기() {
        Parent parent = Parent.builder().loginId("pt1").phoneNumber("111").build();
        parentRepository.save(parent);
        Optional<Parent> saveParent = parentRepository.findByLoginId("pt1");

        Child child = Child.builder().loginId("pc1").phoneNumber("222").build();
        childRepository.save(child);
        Optional<Child> saveChild = childRepository.findByLoginId("pc1");

        ParentAndChild parentAndChild = ParentAndChild.builder().child(saveChild.get()).parent(saveParent.get()).build();
        parentAndChildRepository.save(parentAndChild);
        em.flush();
        em.clear();

        Child saveChild2 = childRepository.fetchMyParentListByLoginId("pc1").get();
        List<ParentAndChild> myParentList = saveChild2.getMyParentList();
        Parent parent1 = myParentList.get(0).getParent();

        Optional<ParentAndChild> byId = parentAndChildRepository.findById(1L);
        Assertions.assertThat(parent1).isEqualTo(byId.get().getParent());

    }
}
