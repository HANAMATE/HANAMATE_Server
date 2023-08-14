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

    private Long parentIdx;
    private Long childrenIdx;
    @Setter
    private Integer allowanceAmount;

    @Setter
    private Integer transferDate; /* 한달에 한번 */
    @Setter
    private Integer dayOfWeek; /* 매주 O요일 */
    @Setter
    private Boolean everyday; /* 매일 */

    @Setter
    private boolean valid;
}
