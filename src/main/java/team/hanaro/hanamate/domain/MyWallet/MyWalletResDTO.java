package team.hanaro.hanamate.domain.MyWallet;

import lombok.*;
import team.hanaro.hanamate.entities.Wallets;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyWalletResDTO {
    private Long walletId;
    private Boolean walletType;
    private Long balance;
    private Long targetAmount;

    public MyWalletResDTO fromWalletsEntity(Wallets wallets){
        MyWalletResDTO myWalletResDTO = MyWalletResDTO.builder().
                walletId(wallets.getWalletId())
                .walletType(wallets.getWalletType())
                .balance(wallets.getBalance())
                .targetAmount(wallets.getTargetAmount()).build();
        return myWalletResDTO;
    }
}
