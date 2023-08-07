package team.hanaro.hanamate.domain.Allowance;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(Long aLong);
}
