package team.hanaro.hanamate.domain.Allowance.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class RequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        @NotBlank(message = "유저 아이디가 비었습니다.")
        private String userId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "아이 아이디가 비었습니다.")
        private String childId;
        @NotBlank(message = "부모 아이디가 비었습니다.") // ansik: 2023/08/11 parentId 추가
        private String parentId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        private Integer allowanceAmount;
        private String requestDescription;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Approve {
        @NotNull(message = "용돈 조르기 요청 Id를 입력해주세요.")
        private Long requestId;
        @NotNull(message = "업데이트 할 상태값이(승인/거절) 비었습니다.")
        private Boolean askAllowance;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Periodic {
        @NotBlank(message = "부모 Id를 입력해주세요.")
        private String parentId;
        @NotBlank(message = "아이 Id를 입력해주세요.")
        private String childId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        private Integer allowanceAmount;

        @PositiveOrZero(message = "transferDate에 0이상의 양수 값을 입력해주세요.")
        private Integer transferDate; /* 한달에 한번, 0이면 아님 */
        @PositiveOrZero(message = "dayOfWeek에 0이상의 양수 값을 입력해주세요.")
        private Integer dayOfWeek; /* 매주 O요일, 0이면 아님 */
        @NotNull
        private Boolean everyday; /* 매일, false면 아님 */
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePeriodic {
        @NotNull(message = "정기 용돈 Id를 입력해주세요.")
        private Long allowanceId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        private Integer allowanceAmount;

        @PositiveOrZero(message = "transferDate에 0이상의 양수 값을 입력해주세요.")
        private Integer transferDate; /* 한달에 한번, 0이면 아님 */
        @PositiveOrZero(message = "dayOfWeek에 0이상의 양수 값을 입력해주세요.")
        private Integer dayOfWeek; /* 매주 O요일, 0이면 아님 */
        @NotNull
        private Boolean everyday; /* 매일, false면 아님 */
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Allowance {
        @NotNull
        private Long allowanceId;
    }
}
