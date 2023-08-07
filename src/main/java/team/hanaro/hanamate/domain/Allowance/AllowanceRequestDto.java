package team.hanaro.hanamate.domain.Allowance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class AllowanceRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChildRequestList {
        @NotBlank
        private Long userId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChildRequest {
        @NotBlank
        private Long userId;
        @NotBlank
        private Integer allowanceAmount;
        private String requestDescription;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentApprove {
        @NotBlank
        private Long walletId;
        @NotBlank
        private Long requestId;
        @NotBlank
        private Boolean askAllowance;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendAllowance {
        @NotBlank
        private Long userId;
        @NotBlank
        private Long childId;
        @NotBlank
        private Integer amount;
    }
}
