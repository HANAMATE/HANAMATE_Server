package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

public class MoimWalletRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class JoinMoimWalletDTO {
        //TODO: 입력값 Validation Check
        @NotBlank(message = "유저 아이디가 비어있거나 공백문자이면 안됩니다.")
        private String userId;

        @NotBlank(message = "모임통장 이름이 비어있거나 공백문자이면 안됩니다.")
        private String walletName;

        @Nullable
        @PositiveOrZero(message = "0 이상 양수값을 입력해야 합니다.")
        private Integer targetAmount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class findAllMoimWalletDTO {
        //TODO: 입력값 Validation Check
        @NotEmpty(message = "유저 아이디가 비어있거나 공백문자이면 안됩니다.")
        private String userId;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AccountBalance {
        @NotBlank
        private Long memberId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class RequestAmount {
        @NotBlank
        private Long memberId;

        @NotBlank
        private Long walletId;
        @NotBlank
        private Integer amount;
    }



}
