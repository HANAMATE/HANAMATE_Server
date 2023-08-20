package team.hanaro.hanamate.domain.Loan.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;


public class LoanRequestDto {

    @Getter
    @Setter
    public static class Apply{

        private String childrenId;
        private String parentId; //아이와 연결되어있는 부모 아이디를 가져오기
        @NotEmpty(message = "대출 이름은 필수 입력값입니다.")
        private String loanName;
        @NotEmpty(message = "대출 금액은 필수 입력값입니다.")
        private String loanAmount;
        private Integer interestRate; //고정 이자로 1 들어감
//        private Boolean valid; // 일단 신청할떄는 null로
        private Boolean completed; //처음 들어갈 때는 0의 값으로

        private String paymentMethod;
//        private Integer duration; //기한
        private String loanMessage;
        private Integer sequence; /* 상환 회차 */
        private Integer total_interestRate;
        private Integer total_repaymentAmount;
        private ArrayList<Integer> repaymentList;

    }

    @Getter
    @Setter
    public static class Calculate{
        private Integer allowanceAmount;
        private ArrayList<Integer> loanAmountList;
        private Integer loanAmount;

        private Integer interestRate;
        private Integer sequence;
        private double balance;

    }
    @Getter
    @Setter
    public static class Approve{

        private Integer duration; //기한
        private Timestamp startDate; //
        private Timestamp endDate;
    }


}
