package team.hanaro.hanamate.domain.MyWallet;

import lombok.*;
import team.hanaro.hanamate.entities.Wallets;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyWalletResDto {
    private Long walletId;
    private Boolean walletType;
    private Long balance;
    private Long targetAmount;

    public MyWalletResDto fromWalletsEntity(Wallets wallets) {
        MyWalletResDto myWalletResDTO = MyWalletResDto.builder().
                walletId(wallets.getWalletId())
                .walletType(wallets.getWalletType())
                .balance(wallets.getBalance())
                .targetAmount(wallets.getTargetAmount()).build();
        return myWalletResDTO;
    }
}
