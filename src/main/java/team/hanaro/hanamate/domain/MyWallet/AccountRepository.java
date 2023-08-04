package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.hanaro.hanamate.entities.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByMemberId(Long aLong);

    @Modifying
    @Query("update Account a set a.balance=:balance where a.memberId=:memberId")
    int updateByMemberId(Long memberId, Integer balance);

    
}
