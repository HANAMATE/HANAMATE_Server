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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    private Long userId;
    private Boolean walletType; /* 0: 개인, 1: 모임통장 */
    private Integer balance;
    private Integer targetAmount; /* 모임통장일 때 목표금액 */
}
