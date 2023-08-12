package team.hanaro.hanamate.domain.MyWallet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Loans;

public interface LoanRepository extends JpaRepository<Loans, Long> {

}
