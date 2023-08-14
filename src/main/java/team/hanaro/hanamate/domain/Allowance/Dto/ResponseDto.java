package team.hanaro.hanamate.domain.Allowance.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.hanaro.hanamate.entities.Allowances;
import team.hanaro.hanamate.entities.Requests;

import java.sql.Timestamp;

public class ResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long requestId;
        private Long targetIdx;
        private Long requesterIdx;
        private Boolean askAllowance;
        private Integer allowanceAmount;
        private Timestamp requestDate;
        private Timestamp expirationDate;
        private Timestamp chagnedDate;
        private String requestDescription;

        public Request(Requests requests) {
            requestId = requests.getRequestId();
            targetIdx = requests.getTargetIdx();
            requesterIdx = requests.getRequesterIdx();
            allowanceAmount = requests.getAllowanceAmount();
            askAllowance = requests.getAskAllowance();
            requestDate = requests.getRequestDate();
            expirationDate = requests.getExpirationDate();
            chagnedDate = requests.getChangedDate();
            requestDescription = requests.getRequestDescription();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Allowance {
        private Long allowanceId;

        private Long parentIdx;
        private Long childrenIdx;
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private Integer dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */

        public Allowance(Allowances allowances) {
            allowanceId = allowances.getAllowanceId();
            parentIdx = allowances.getParentIdx();
            childrenIdx = allowances.getChildrenIdx();
            allowanceAmount = allowances.getAllowanceAmount();
            transferDate = allowances.getTransferDate();
            dayOfWeek = allowances.getDayOfWeek();
            everyday = allowances.getEveryday();
        }
    }

}
