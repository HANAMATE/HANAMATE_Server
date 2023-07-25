package team.hanaro.hanamate.domain.Signup;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.domain.Member.MemberEntity;

public interface SignupRepository extends JpaRepository<MemberEntity, Long> {
}
