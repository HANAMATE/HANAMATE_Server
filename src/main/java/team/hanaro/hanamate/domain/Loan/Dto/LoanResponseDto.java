package team.hanaro.hanamate.domain.Loan.Dto;

import lombok.Getter;
import lombok.Setter;
import team.hanaro.hanamate.entities.LoanHistory;
import team.hanaro.hanamate.entities.Loans;

import java.sql.Timestamp;
import java.util.ArrayList;

public class LoanResponseDto {
    @Getter
    @Setter
//    @AllArgsConstructor
    public static class initInfo {
        private Integer interestRate;
        private String paymentMethod;

        private Integer canAmount_3month;

        private Integer canAmount_6month;

        private Integer canAmount_12month;
    }

    @Getter
    @Setter
    public static class CalculateResult{
        private ArrayList<Integer> loanAmountList;
        private ArrayList<Integer> repaymentList;
        private Integer total_interestRate;
        private Integer total_repaymentAmount;


    }
    @Getter
    @Setter
    public static class applyInfo {
        private String userType;
        private String loanName;
        private Integer loanAmount;
        private String loanMessage;
        private Integer sequence;
        private Boolean valid;

    }

    @Getter
    @Setter
    public static class applyNotInfo {
        private String userType;
    }

    @Getter
    @Setter
    public static class approve{
        private Boolean valid;
    }

    @Getter
    @Setter
    public static class historyInfo{
        private Long loanId;
        private String loanName;
        private Integer loanAmount;
        private Timestamp endDate;

        public historyInfo(Loans loans){

            loanId = loans.getLoanId();
            loanName = loans.getLoanName();
            loanAmount=loans.getLoanAmount();
            endDate=loans.getEndDate();

        }
    }

    @Getter
    @Setter
    public static class historydetailInfo{
        private Integer sequence_time;
        private Timestamp transactionDate;
        private Integer repaymentAmount;

        public historydetailInfo(LoanHistory loanHistory){

            sequence_time = loanHistory.getSequence_time();
            transactionDate=loanHistory.getTransactionDate();
            repaymentAmount=loanHistory.getRepaymentAmount();

        }
    }

    @Getter
    @Setter
    public static class loandetailInfo{
        private String loanName;
        private Integer loanAmount;
        private String loanMessage;
        private Integer interestRate;
        private Integer sequence;
        private Integer total_interestRate;
        private Integer total_repaymentAmount;
        private String paymentMethod;
        private Timestamp startDate;
        private Timestamp endDate;


    }
}
