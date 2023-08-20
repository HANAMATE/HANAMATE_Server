package team.hanaro.hanamate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonIgnore //순환참조 문제 해결을 위한 JsonIgnore -> 해당 값을 DTO에 반환하기 위해서 get으로 값을 꺼내서 직접 넣어주세요.
    private Parent parent;

    // TODO: 2023/08/19 cascade 옵션 테스트가 필요합니다. 우선 적용해놓을테니 대출 삭제 api 완성되면 꼭 알려주세요!!!!!!!!! 
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "child_id")
    @JsonIgnore //순환참조 문제 해결을 위한 JsonIgnore -> 해당 값을 DTO에 반환하기 위해서 get으로 값을 꺼내서 직접 넣어주세요.
    private Child child;

    private Long walletId; //내지갑
    private String loanName; //대출 이름
    private Integer duration; //대출 기한
    private Timestamp startDate; //시작날짜
    private Timestamp endDate; //마감날짜
    private Integer loanAmount; //대출 금액
    private Integer balance; //잔액
//    private Integer repaymentAmount; //상환금액 TODO: 어떤 컬럼인지 확인

    private String paymentMethod;
    private Integer interestRate;// 고정 이자 /* 5%면 5 */
    private Integer sequence; /* 상환 회차 */
    private Boolean valid; //요청 확인  초기에는 null값 (승인 1/ 거절 0)
    private Boolean completed; //상환이 마무리 됐는지 여부(마무리됨 1/마무리 안됨 0)
    private String loanMessage; //요청 메세지
    private Integer total_interestRate; //총이자
    private Integer total_repaymentAmount; //총상환금액
//    private Integer month_loanAmount; //매달 상환금액
}
