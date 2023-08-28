package team.hanaro.hanamate.domain.MyWallet.Dto;

import lombok.*;
import team.hanaro.hanamate.entities.Account;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.MyWallet;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;


public class ResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Wallet {
        private Long walletId;
        private String walletType;
        private Integer balance;
        private Integer targetAmount;

        public Wallet(MyWallet wallets) {
            walletId = wallets.getId();
            // TODO: 2023/08/13 아래 부분 wallet ==> Mywallet , MoimWallet으로 바뀌면서 없는 부분임
            walletType=wallets.getDecriminatorValue();
            balance = wallets.getBalance();
            if ("moim".equals(walletType)) {
                MoimWallet temp = (MoimWallet) wallets;
                targetAmount = temp.getTargetAmount();
            }
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
        private String message;

        public MyTransactions(Transactions transactions) {
            id = transactions.getId();
            walletId = transactions.getWallet().getId();
            date = transactions.getTransactionDate();
            amount = transactions.getAmount();
            balance = transactions.getBalance();
            type = transactions.getTransactionType();
            message = transactions.getMessage();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AccountInfo {
        private Long accountId;
        private String name;
        private Integer balance;

        public AccountInfo(Account account) {
            accountId = account.getAccountId();
            name = account.getName();
            balance = account.getBalance();
        }
    }

}
