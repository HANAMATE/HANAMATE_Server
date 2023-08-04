package team.hanaro.hanamate.domain.MyWallet;

import lombok.*;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyWalletTransactionResDto {
    private Long id;
    private Long walletId;
    private Timestamp transactionDate;
    private Integer amount;
    private Integer balance;
    private String transactionType;

    public MyWalletTransactionResDto(Transactions transactions) {
        id = transactions.getId();
        walletId = transactions.getWalletId();
        transactionDate = transactions.getTransactionDate();
        amount = transactions.getAmount();
        balance = transactions.getBalance();
        transactionType = transactions.getTransactionType();
    }
}
