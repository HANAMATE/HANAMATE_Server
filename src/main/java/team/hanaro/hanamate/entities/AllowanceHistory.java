package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "allowance_history")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllowanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long historyId;
    private Long allowanceId;
    private Integer allowanceAmount;
    private Timestamp transactionDate;
    private Boolean success;
}
