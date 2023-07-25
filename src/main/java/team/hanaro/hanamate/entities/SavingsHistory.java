package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "savings_history")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long historyId;
    private Long savingsId;
    private Integer sequence;
    private Integer depositAmount;
    private Timestamp transactionDate;
    private Integer balance;
    private Boolean success;
}
