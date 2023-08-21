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
        @Max(value = Integer.MAX_VALUE, message = "숫자가 너무 큽니다! 21억 이하를 입력하세요.")
        private Integer targetAmount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class UpdateMoimWalletInfoRequestDTO {
        //TODO: 입력값 Validation Check
        @NotBlank(message = "유저 아이디가 비어있거나 공백문자이면 안됩니다.")
        private String userId;

        @NotNull(message = "모임통장 번호가 입력되어야 합니다.")
        private Long moimWalletId;

        @NotBlank(message = "모임통장 이름이 비어있거나 공백문자이면 안됩니다.")
        private String walletName;

        @Nullable
        @PositiveOrZero(message = "0 이상 양수값을 입력해야 합니다.")
        @Max(value = Integer.MAX_VALUE, message = "숫자가 너무 큽니다! 21억 이하를 입력하세요.")
        private Integer targetAmount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class findAllMoimWalletDTO {
        //TODO: 입력값 Validation Check
        @NotBlank(message = "유저 아이디가 비어있거나 공백문자이면 안됩니다.")
        private String userId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class RequestAmount {
        @NotNull
        private Long memberId;

        @NotNull
        private Long walletId;
        @NotNull
        private Integer amount;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DeleteRequestDTO {
        @NotNull
        private Long requestId;
    }

//    public static class DeleteArticleRequestDTO{
//        @NotNull
//        private Long ArticleId;
//    }


}
