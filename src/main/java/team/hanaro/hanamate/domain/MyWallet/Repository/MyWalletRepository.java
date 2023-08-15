package team.hanaro.hanamate.domain.MyWallet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.entities.MyWallet;

import java.util.Optional;

public interface MyWalletRepository  extends JpaRepository<MyWallet,Long> {
    @Override
    Optional<MyWallet> findById(Long aLong);
}
