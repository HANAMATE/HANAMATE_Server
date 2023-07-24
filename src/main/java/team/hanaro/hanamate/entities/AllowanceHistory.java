package team.hanaro.hanamate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "allowance_history")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AllowanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long historyId;
    private Long allowanceId;
    private Integer allowanceAmount;
    private Timestamp transactionDate;
    private Boolean success;
}
