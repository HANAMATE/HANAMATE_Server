package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Requests extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private Long targetIdx;
    private Long requesterIdx;
    private Long loanId;
    private Long savingsId;
    @Setter
    private Boolean askAllowance;
    private Integer allowanceAmount;
    private LocalDateTime expirationDate;
    private Integer result;
    private String requestDescription;

}
