package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallets_and_users")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletsAndMember {
    @Id
    private Long walletId;

    private Long memberId;
}
