package team.hanaro.hanamate.domain.Loan.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


public class LoanRequestDto {

    @Getter
    @Setter
    public static class Apply{

        private String childrenId;

        @NotEmpty(message = "대출 이름은 필수 입력값입니다.")
        private String loanName;

        @NotEmpty(message = "대출 금액은 필수 입력값입니다.")
        private String loanAmount;

//        @NotEmpty(message = "잘못된 요청입니다.")
//        private String accessToken;


    }



}