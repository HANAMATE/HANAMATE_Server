package team.hanaro.hanamate.domain.Loan.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Child;
import team.hanaro.hanamate.entities.Loans;
import team.hanaro.hanamate.entities.Parent;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findByLoanId(String id);
    Optional<Loans> findByChild(Child id);
    Optional<Loans> findByParent(Parent id);

    void deleteById(Long loanId);
}
