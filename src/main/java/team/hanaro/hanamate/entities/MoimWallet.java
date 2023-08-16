package team.hanaro.hanamate.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("moim")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MoimWallet extends MyWallet {

    //목표 금액
    @Setter
    @Column(name = "target_amount")
    private Integer targetAmount;

    @Setter
    @Column(name = "wallet_name")
    private String walletName;

    @Builder.Default
    @OneToMany(mappedBy = "moimWalletId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoimWalletAndUser> moimWalletAndUsers = new ArrayList<>();

    //비즈니스 로직

//    public void updateTargetAmount(int target_amount) {
//        this.targetAmount = target_amount;
//    }
//    public void addWalletsAndMember(MoimWalletAndUser wam) {
//        this.moimWalletAndUsers.add(wam);
//        wam.setMoimWalletId(this.getId());
//    }
}
