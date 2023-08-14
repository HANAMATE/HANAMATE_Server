package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "requests")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Requests {
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
    private Timestamp requestDate;
    private Timestamp expirationDate;
    @Setter
    private Timestamp changedDate;
    private Integer result;
    private String requestDescription;

}
