package team.hanaro.hanamate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "savings")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Savings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long savingsId;

    private Long childrenId;
    private Long parentId;
    private Long walletId;
    private String savingsName;
    private Integer duration;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer targetAmount;
    private Integer balance;
    private Integer depositAmount;

    private Integer transferDate; /* 한달에 한번 */
    private String dayOfWeek;/* 매주 O요일 */
    private Boolean everyday; /* 매일 */

    private Integer interestRate;
    private Integer delinquentAmount;
    private Integer delinquentCount;

    private Integer sequence;
    private Boolean valid;
    private Boolean completed;
}
