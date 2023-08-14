package team.hanaro.hanamate.domain.Allowance.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class RequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        @NotBlank(message = "유저 아이디가 비었습니다.")
        private Long userId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "아이 아이디가 비었습니다.")
        private Long childId;
        @NotBlank(message = "부모 아이디가 비었습니다.") // ansik: 2023/08/11 parentId 추가
        private Long parentId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        @NotBlank(message = "요청 금액이 비었습니다.")
        private Integer allowanceAmount;
        private String requestDescription;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Approve {
        @NotBlank(message = "용돈 조르기 요청 Id를 입력해주세요.")
        private Long requestId;
        @NotBlank(message = "업데이트 할 상태값이(승인/거절) 비었습니다.")
        private Boolean askAllowance;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Periodic {
        @NotBlank(message = "부모 Id를 입력해주세요.")
        private Long parentId;
        @NotBlank(message = "아이 Id를 입력해주세요.")
        private Long childrenId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        @NotBlank(message = "정기 용돈 금액이 비었습니다.")
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private String dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePeriodic {
        @NotBlank(message = "정기 용돈 Id를 입력해주세요.")
        private Long allowanceId;
        @Positive(message = "요청 금액은 1이상의 양수 값을 입력해주세요.")
        @NotBlank(message = "정기 용돈 금액이 비었습니다.")
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private String dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Allowance {
        @NotBlank
        private Long allowanceId;
    }
}
