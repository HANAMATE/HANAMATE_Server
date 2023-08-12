package team.hanaro.hanamate.domain.MyWallet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.MyWallet;

public interface MyWalletRepository  extends JpaRepository<MyWallet,Long> {
}
