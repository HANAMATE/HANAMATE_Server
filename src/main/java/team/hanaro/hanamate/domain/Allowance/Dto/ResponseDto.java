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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeriodicAllowance {
        private Long allowanceId;

        private Long parentId;
        private Long childrenId;
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private String dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */

        public PeriodicAllowance(Allowances allowances) {
            allowanceId = allowances.getAllowanceId();
            parentId = allowances.getParentId();
            childrenId = allowances.getChildrenId();
            allowanceAmount = allowances.getAllowanceAmount();
            transferDate = allowances.getTransferDate();
            dayOfWeek = allowances.getDayOfWeek();
            everyday = allowances.getEveryday();
        }
    }

}
