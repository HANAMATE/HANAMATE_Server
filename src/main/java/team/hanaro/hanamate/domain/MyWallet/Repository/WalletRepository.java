package team.hanaro.hanamate.domain.MyWallet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.MyWallet;
import team.hanaro.hanamate.entities.Wallets;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<MyWallet, Long> {

    @Override
    Optional<MyWallet> findById(Long aLong);

//    Optional<MyWallet> findByUserId(Long aLong);

    @Transactional
    @Modifying
    @Query("update Wallets w set w.balance=:balance where w.walletId=:walletId")
    int updateByWalletId(Long walletId, Integer balance);
}
