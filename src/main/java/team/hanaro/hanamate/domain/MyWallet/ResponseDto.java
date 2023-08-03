package team.hanaro.hanamate.domain.MyWallet;

import lombok.*;
import team.hanaro.hanamate.entities.Transactions;
import team.hanaro.hanamate.entities.Wallets;

import java.sql.Timestamp;


public class ResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MyWallet {
        private Long walletId;
        private Boolean walletType;
        private Long balance;
        private Long targetAmount;

        public MyWallet(Wallets wallets) {
            walletId = wallets.getWalletId();
            walletType = wallets.getWalletType();
            balance = wallets.getBalance();
            targetAmount = wallets.getTargetAmount();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MyTransactions {
        private Long id;
        private Long walletId;
        private Timestamp date;
        private Integer amount;
        private Integer balance;
        private String type;

        public MyTransactions(Transactions transactions) {
            id = transactions.getId();
            walletId = transactions.getWalletId();
            date = transactions.getDate();
            amount = transactions.getAmount();
            balance = transactions.getBalance();
            type = transactions.getType();
        }
    }

}
