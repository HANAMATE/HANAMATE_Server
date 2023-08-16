package team.hanaro.hanamate.domain.Loan.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Time;
import java.sql.Timestamp;


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
//
//        private Timestamp startDate; //시작 날짜는 부모쪽에서 승인해주면 그떄부터이기 때문에 제거
//        private Timestamp endDate;

        private Integer duration; //기한

        private String loanMessage;

    }


}
