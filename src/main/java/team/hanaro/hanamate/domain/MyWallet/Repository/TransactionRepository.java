package team.hanaro.hanamate.domain.MyWallet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    List<Transactions> findAllByWalletIdAndTransactionDateBetween(Long aLong, Timestamp start, Timestamp end);

    List<Transactions> findAllByWalletId(Long aLong);
}
