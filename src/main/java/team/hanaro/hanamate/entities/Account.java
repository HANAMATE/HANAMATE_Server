package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "allowance_history")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private Long accountId;

    private Long memberId;
    private String name;
    private Timestamp openDate;
    private Integer balance;
}
