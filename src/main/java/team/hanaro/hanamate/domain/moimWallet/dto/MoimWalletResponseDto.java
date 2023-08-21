package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSResponseDTO.ArticleResponseDTO;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;
import java.util.List;


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

    @NoArgsConstructor
    @SuperBuilder
    @Data
    public static class MoimWalletTransactionsDTO {
        private Long id;
        private Long walletId;
        private Timestamp date;
        private Integer amount;
        private Integer balance;
        private String type;

        private ArticleResponseDTO article;

        public MoimWalletTransactionsDTO(Transactions transactions) {
            id = transactions.getId();
            walletId = transactions.getWallet().getId();
            date = transactions.getTransactionDate();
            amount = transactions.getAmount();
            balance = transactions.getBalance();
            type = transactions.getTransactionType();
        }
    }




    @Data
    @SuperBuilder
    @ToString
    @NoArgsConstructor
    public static class MoimWalletDTO {
        private Long walletId;
        private String walletName;
        private String walletType;
        private Integer balance;
        private Integer targetAmount;
        private List<MoimWalletTransactionsDTO> transactionList;

        public MoimWalletDTO(MoimWallet moimWallet) {
            walletId = moimWallet.getId();
            walletName = moimWallet.getWalletName();
            walletType = moimWallet.getDecriminatorValue();
            balance = moimWallet.getBalance();
            targetAmount = moimWallet.getTargetAmount();
        }
        }
    @Data
    public static class UpdateMoimWalletInfoResponseDTO {
        private Long walletId;
        private String walletName;
        private String walletType;

        public UpdateMoimWalletInfoResponseDTO(MoimWallet moimWallet) {
            walletId = moimWallet.getId();
            walletName = moimWallet.getWalletName();
            walletType = moimWallet.getDecriminatorValue();
        }
    }

}
