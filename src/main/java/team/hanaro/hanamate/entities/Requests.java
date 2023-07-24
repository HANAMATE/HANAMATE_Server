package team.hanaro.hanamate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "requests")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private Long targetId;
    private Long requesterId;
    private Long loanId;
    private Long savingsId;
    private Boolean askAllowance;
    private Integer allowanceAmount;
    private Timestamp requestDate;
    private Timestamp expirationDate;
    private Integer result;
    private String requestDescription;

}
