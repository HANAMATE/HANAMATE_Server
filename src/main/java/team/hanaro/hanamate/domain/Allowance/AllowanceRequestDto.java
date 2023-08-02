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
}
