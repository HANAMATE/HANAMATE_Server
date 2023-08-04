package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MyWalletTransactionsRepository extends JpaRepository<Transactions, Long> {

    Optional<List<Transactions>> findAllByWalletId(Long aLong);

    Optional<List<Transactions>> findAllByWalletIdAndTransactionDateBetween(Long aLong, Timestamp start, Timestamp end);
}
