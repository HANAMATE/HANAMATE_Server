package team.hanaro.hanamate.domain.Loan.Dto;

import lombok.Getter;
import lombok.Setter;

public class LoanResponseDto {
    @Getter
    @Setter
//    @AllArgsConstructor
    public static class initInfo {
        private Integer interestRate;
        private String paymentMethod;

    }

//    @Builder
//    @Getter
//    @AllArgsConstructor
//    public static class TokenInfo {
//        private String grantType;
//        private String accessToken;
//        private String refreshToken;
//        private Long refreshTokenExpirationTime;
//    }
}