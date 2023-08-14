package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wallets_and_users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletsAndMember {
    @Id @GeneratedValue
    @Column(name = "wallet_and_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private MoimWallet moimWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
/**
 *  유저는 한가지 개인 지갑을 가진다.
 *  유저는 여러가지 모임 통장을 가질 수 있다.
 *
 *  모임통장은 여러 유저를 포함할 수 있다.
 * */
