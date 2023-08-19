package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loans")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private User children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private User parent;

    private Long walletId; //내지갑
    private String loanName; //대출 이름
    private Integer duration; //대출 기한
    private Timestamp startDate; //시작날짜
    private Timestamp endDate; //마감날짜
    private Integer loanAmount; //대출 총 금액
    private Integer balance; //잔액
    private Integer repaymentAmount; //상환금액 TODO: 어떤 컬럼인지 확인

    private String paymentMethod; // TODO: 원리금균등상환만 하는데 컬럼 따로 필요한지 확인
    private Integer interestRate;// 고정 이자 /* 5%면 5 */
    private Integer allowanceRate; //총 상환 이자 금액
    private Integer delinquentAmount; /* 연체 금액 */
//    private Integer delinquentCount; //연체횟수 TODO: ? 연체 횟수인지 확인 => 정기 용돈에서 상환되어 연체될 상황 X
    private Integer sequence; /* 상환 회차 */
    private Boolean valid; //요청 확인  초기에는 null값 (승인 1/ 거절 0)
    private Boolean completed; //상환이 마무리 됐는지 여부(마무리됨 1/마무리 안됨 0)
    private String loanMessage; //요청 메세지
}
