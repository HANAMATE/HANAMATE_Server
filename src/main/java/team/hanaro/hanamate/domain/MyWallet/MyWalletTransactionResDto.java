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
    private Timestamp date;
    private Integer amount;
    private Integer balance;
    private String type;

    public MyWalletTransactionResDto fromTransactionsEntity(Transactions transactions) {
        MyWalletTransactionResDto myWalletTransactionResDTO = MyWalletTransactionResDto.builder().
                id(transactions.getId())
                .walletId(transactions.getWalletId())
                .date(transactions.getDate())
                .amount(transactions.getAmount())
                .balance(transactions.getBalance())
                .type(transactions.getType()).build();
        return myWalletTransactionResDTO;
    }
}
