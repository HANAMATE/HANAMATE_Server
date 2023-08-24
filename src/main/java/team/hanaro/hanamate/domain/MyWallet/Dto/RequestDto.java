package team.hanaro.hanamate.domain.MyWallet.Dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RequestDto {

    @Data
    public static class Charge {
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        private Integer amount;
    }

    @Data
    public static class AccountInfo {
        @NotNull(message = "계좌 아이디가 비었습니다.")
        private Long accountId;
        @NotBlank
        private String name;
    }

    @Data
    public static class Transfer {
        @NotNull(message = "보내는 사람 지갑 아이디가 비었습니다.")
        private Long sendWalletId;
        @NotNull(message = "받는 사람 지갑 아이디가 비었습니다.")
        private Long receiveWalletId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        private Integer amount;
    }

}
