package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

public class RequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MoimWallet {
        //TODO: 입력값 Validation Check

        //userid
        @NotNull(message = "유저 아이디가 비었습니다.")
        private Long userId;

        @Nullable
        @PositiveOrZero(message = "0 이상 양수값을 입력해야 합니다.")
        private Integer target_amount;

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
