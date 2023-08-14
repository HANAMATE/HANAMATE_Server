package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wallets_and_users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoimWalletAndUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_and_user_id")
    private Long id;

    @Column(name = "moim_wallet_id")
    @Setter
    private Long moimWalletId;

    @Column(name = "user_id")
    @Setter
    private Long userId;

}
/**
 * 유저는 한가지 개인 지갑을 가진다.
 * 유저는 여러가지 모임 통장을 가질 수 있다.
 * <p>
 * 모임통장은 여러 유저를 포함할 수 있다.
 * <p>
 * 1. db상으로 2개 pk (복합키) -> 유니크 제약조건이 걸려서 막히는게 나을까?
 * <p>
 * 2. 의미없는 pk1개, 로직으로 findByMoilanduserid().isNull-> 로직상 체크만하고 디비에 넣을까..
 */
