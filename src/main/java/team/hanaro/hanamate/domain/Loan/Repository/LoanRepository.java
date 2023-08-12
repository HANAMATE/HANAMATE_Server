package team.hanaro.hanamate.domain.Loan.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Loans;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findByLoanId(String id);

}