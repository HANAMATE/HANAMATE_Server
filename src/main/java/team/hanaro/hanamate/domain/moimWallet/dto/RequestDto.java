package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public class RequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MoimWallet {
        //TODO: 입력값 Validation Check

        //userid
        @NotBlank
        private Long userId;

        @Nullable
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
