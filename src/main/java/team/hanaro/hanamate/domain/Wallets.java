package team.hanaro.hanamate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wallets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long walletId;

    @Enumerated(EnumType.STRING)
    private boolean walletType; /* 0: 개인, 1: 모임통장 */
    private Long balance;
    private Long targetAmount; /* 모임통장일 때 목표금액 */
}
