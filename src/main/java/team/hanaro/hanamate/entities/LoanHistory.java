package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loan_history")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="loanId")
    private Loans loans;
    private Integer sequence_time;
    private Timestamp transactionDate;
    private Integer repaymentAmount; //상환금액
    private Boolean success;
}
