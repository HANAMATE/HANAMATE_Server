package team.hanaro.hanamate.domain.Loan.Dto;

import lombok.Getter;
import lombok.Setter;

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
        private Integer total_loanAmount;


    }

}