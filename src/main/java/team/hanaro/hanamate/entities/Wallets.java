package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long walletId;

    private boolean walletType; /* 0: 개인, 1: 모임통장 */
    private Long balance;
    private Long targetAmount; /* 모임통장일 때 목표금액 */
}
