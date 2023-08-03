package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "accounts")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private Long memberId;

    private Long accountId;
    private String name;
    private Timestamp openDate;
    private Integer balance;
}
