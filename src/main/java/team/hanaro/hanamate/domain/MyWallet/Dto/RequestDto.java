package team.hanaro.hanamate.domain.MyWallet.Dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class User {
        @NotEmpty(message = "유저 아이디가 비었습니다.")
        private String userId;
        @Nullable
        private Integer year;
        @Nullable
        private Integer month;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Charge {
        @NotEmpty(message = "유저 아이디가 비었습니다.")
        private String userId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        private Integer amount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AccountInfo {
        @NotEmpty(message = "유저 아이디가 비었습니다.")
        private String userId;
        @NotNull(message = "계좌 아이디가 비었습니다.")
        private Long accountId;
        @NotBlank
        private String name;
    }

}
