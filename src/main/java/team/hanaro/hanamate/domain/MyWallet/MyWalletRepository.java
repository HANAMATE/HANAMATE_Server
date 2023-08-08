package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.Wallets;

import java.util.Optional;

public interface MyWalletRepository extends JpaRepository<Wallets, Long> {

    @Override
    Optional<Wallets> findById(Long aLong);

    @Transactional
    @Modifying
    @Query("update Wallets w set w.balance=:balance where w.walletId=:walletId")
    int updateByWalletId(Long walletId, Long balance);
}
