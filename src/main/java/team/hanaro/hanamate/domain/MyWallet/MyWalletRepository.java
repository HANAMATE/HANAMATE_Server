package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.hanaro.hanamate.entities.Wallets;

import java.util.Optional;

//@Repository
public interface MyWalletRepository extends JpaRepository<Wallets, Long> {
    @Override
    Optional<Wallets> findById(Long aLong);
}
