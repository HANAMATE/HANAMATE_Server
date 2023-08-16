package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import team.hanaro.hanamate.entities.Account;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;


public class MoimWalletResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MoimWalletList {
        private Long walletId;
        private String walletName;
        private String walletType;
        private Integer balance;
        private Integer targetAmount;

        //내가 가입한 모든 모임 월렛 조회용
        public MoimWalletList(MoimWallet wallets) {
            walletId = wallets.getId();
            walletName = wallets.getWalletName();
            walletType = wallets.getDecriminatorValue();
            balance = wallets.getBalance();
            if ("moim".equals(walletType)) {
                targetAmount = ((MoimWallet) wallets).getTargetAmount();
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

        public MyTransactions(Transactions transactions) {
            id = transactions.getId();
            walletId = transactions.getWallet().getId();
            date = transactions.getTransactionDate();
            amount = transactions.getAmount();
            balance = transactions.getBalance();
            type = transactions.getTransactionType();
        }
    }


}
