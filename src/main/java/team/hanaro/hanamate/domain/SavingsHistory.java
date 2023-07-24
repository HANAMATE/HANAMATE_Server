package team.hanaro.hanamate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "savings_history")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
