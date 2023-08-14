package team.hanaro.hanamate.domain.Allowance.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class RequestDto {

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeriodicAllowance {
        @NotBlank
        private Long userId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class makePeriodicAllowance {
        @NotBlank
        private Long parentId;
        @NotBlank
        private Long childrenId;
        @NotBlank
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private String dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updatePeriodicAllowance {
        @NotBlank
        private Long allowanceId;
        @NotBlank
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private String dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deletePeriodicAllowance {
        @NotBlank
        private Long allowanceId;
    }
}
