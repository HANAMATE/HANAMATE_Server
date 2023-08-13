package team.hanaro.hanamate.domain.MyWallet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.MyWallet;

public interface MyWalletRepository<T extends MyWallet>  extends JpaRepository<T,Long> {
    @Transactional
    @Modifying
    @Query("update Wallets w set w.balance=:balance where w.walletId=:walletId")
    int updateByWalletId(Long walletId, Integer balance);
}
