package team.hanaro.hanamate.domain.MyWallet.Dto;

import lombok.*;
import team.hanaro.hanamate.entities.Account;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.MyWallet;
import team.hanaro.hanamate.entities.Transactions;
//import team.hanaro.hanamate.entities.Wallets;

import java.sql.Timestamp;


public class ResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MyWallet {
        private Long walletId;
        private String walletType;
        private Integer balance;
        private Integer targetAmount;

        public MyWallet(team.hanaro.hanamate.entities.MyWallet wallets) {
            walletId = wallets.getId();
            // TODO: 2023/08/13 아래 부분 wallet ==> Mywallet , MoimWallet으로 바뀌면서 없는 부분임
//            walletType = wallets.getWalletType();
            walletType=wallets.getDecriminatorValue();
            balance = wallets.getBalance();
            if ("moim".equals(walletType)) {
                MoimWallet temp = (MoimWallet) wallets;
                targetAmount = temp.getTarget_amount();
            }
//            targetAmount = wallets.getTargetAmount();
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
            walletId = transactions.getWallet().getId();
            date = transactions.getTransactionDate();
            amount = transactions.getAmount();
            balance = transactions.getBalance();
            type = transactions.getTransactionType();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AccountBalance {
        private Long accountId;
        private String name;
        private Integer balance;

        public AccountBalance(Account account) {
            accountId = account.getAccountId();
            name = account.getName();
            balance = account.getBalance();
        }
    }

}
