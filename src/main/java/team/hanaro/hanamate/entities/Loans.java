package team.hanaro.hanamate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loans")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;

    private Long childrenId;
    private Long parentId;
    private Long walletId;
    private String loanName;
    private Integer duration;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer loanAmount;
    private Integer balance;
    private Integer repaymentAmount; // TODO: 어떤 컬럼인지 확인

    private Integer paymentDate;
    private String dayOfWeek;
    private Boolean everyday;

    /*private String paymentMethod;*/ // TODO: 원리금균등상환만 하는데 컬럼 따로 필요한지 확인
    private Integer interestRate; /* 5%면 5 */
    private Integer delinquentAmount; /* 연체 금액 */
    private Integer delinquentCount; // TODO: ? 연체 횟수인지 확인
    private Integer sequence; /* 상환 회차 */
    private Boolean valid;
    private Boolean completed;
}
