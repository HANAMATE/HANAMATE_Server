package team.hanaro.hanamate.domain.moimWallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.MoimWalletAndUser;

import java.util.List;
import java.util.Optional;

public interface MoimWalletAndMemberRepository extends JpaRepository<MoimWalletAndUser,Long> {

    List<MoimWalletAndUser> findAllByUserId(String userId);
//    boolean existsByWalletIdAndUserId(Long walletId, Long userId);
}
