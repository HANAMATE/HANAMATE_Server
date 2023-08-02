package team.hanaro.hanamate.domain.Allowance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.hanaro.hanamate.entities.Requests;

import java.sql.Timestamp;

public class AllowanceResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChildResponseList {
        private Long requestId;
        private Integer allowanceAmount;
        private Boolean askAllowance;
        private Timestamp requestDate;
        private Timestamp expirationDate;

        public ChildResponseList(Requests requests) {
            requestId = requests.getRequestId();
            allowanceAmount = requests.getAllowanceAmount();
            askAllowance = requests.getAskAllowance();
            requestDate = requests.getRequestDate();
            expirationDate = requests.getExpirationDate();
        }
    }
}
