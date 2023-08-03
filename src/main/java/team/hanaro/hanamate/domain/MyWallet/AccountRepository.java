package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByMemberId(Long aLong);
}
