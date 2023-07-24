package team.hanaro.hanamate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallets_and_users")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletsAndUsers {
    @Id
    private Long walletId;

    private Long userId;
}
