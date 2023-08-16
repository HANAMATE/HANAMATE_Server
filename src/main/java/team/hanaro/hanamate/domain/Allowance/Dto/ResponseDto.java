package team.hanaro.hanamate.domain.Allowance.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.hanaro.hanamate.entities.Allowances;
import team.hanaro.hanamate.entities.Requests;

import java.time.LocalDateTime;

public class ResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long requestId;
        private String targetId;
        private String requesterId;
        private Boolean askAllowance;
        private Integer allowanceAmount;
        private LocalDateTime createDate;
        private LocalDateTime modifiedDate;
        private LocalDateTime expirationDate;
        private String requestDescription;

        public Request(Requests requests, String childId, String parentId) {
            requestId = requests.getRequestId();
            targetId = parentId;
            requesterId = childId;
            allowanceAmount = requests.getAllowanceAmount();
            askAllowance = requests.getAskAllowance();
            createDate = requests.getCreateDate();
            expirationDate = requests.getExpirationDate();
            modifiedDate = requests.getModifiedDate();
            requestDescription = requests.getRequestDescription();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Allowance {
        private Long allowanceId;

        private String parentId;
        private String childrenId;
        private Integer allowanceAmount;

        private Integer transferDate; /* 한달에 한번 */
        private Integer dayOfWeek; /* 매주 O요일 */
        private Boolean everyday; /* 매일 */

        public Allowance(Allowances allowances, String childId, String parentId) {
            allowanceId = allowances.getAllowanceId();
            this.parentId = parentId;
            childrenId = childId;
            allowanceAmount = allowances.getAllowanceAmount();
            transferDate = allowances.getTransferDate();
            dayOfWeek = allowances.getDayOfWeek();
            everyday = allowances.getEveryday();
        }
    }

}
