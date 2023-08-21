package team.hanaro.hanamate.domain.Loan.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.*;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findByLoanId(String user);
//    Optional<Loans> findByLoanId(Loans id);
    Optional<Loans> findByChild(Child id);
    Optional<Loans> findByParent(Parent id);

    void deleteById(Long loanId);

    Optional<List<Loans>> findAllByChild(Child nowChild);
    Optional<List<Loans>> findAllByChildAndValidIsTrue(Child child);
    Optional<List<Loans>> findAllByParentAndValidIsTrue(Parent parent);

//    Optional<List<LoanHistory>> findAllByChildAndSuccessIsTrue(Child nowChild);
}
