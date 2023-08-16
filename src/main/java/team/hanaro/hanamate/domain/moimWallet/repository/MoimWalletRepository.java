package team.hanaro.hanamate.domain.moimWallet.repository;

import antlr.collections.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.MoimWallet;

import java.util.Optional;

public interface MoimWalletRepository extends JpaRepository<MoimWallet,Long> {

}
