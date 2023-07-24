package team.hanaro.hanamate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "allowances")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Allowances {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long allowanceId;

    private Long parentId;
    private Long childrenId;
    private Integer allowanceAmount;

    private Integer transferDate; /* 한달에 한번 */
    private String dayOfWeek; /* 매주 O요일 */
    private Boolean everyday; /* 매일 */
}
