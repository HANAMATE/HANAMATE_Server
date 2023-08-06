package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "allowances")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Allowances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allowanceId;

    private Long parentId;
    private Long childrenId;
    private Integer allowanceAmount;

    private Integer transferDate; /* 한달에 한번 */
    private String dayOfWeek; /* 매주 O요일 */
    private Boolean everyday; /* 매일 */
}
