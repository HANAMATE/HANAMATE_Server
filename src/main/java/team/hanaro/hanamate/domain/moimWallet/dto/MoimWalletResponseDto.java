package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import team.hanaro.hanamate.entities.Article;
import team.hanaro.hanamate.entities.Comment;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ArticleResponseDTO {
        private Long id;
        private byte[] imageId;
        private String title;
        private String content;
        private Long likes = 0L;
        private String TransactionName;
        private List<CommentDTO> comments = new ArrayList<>();

        public ArticleResponseDTO(Article article) {
            id = article.getId();
            imageId = article.getImageId();
            title = article.getTitle();
            content = article.getContent();
            likes = article.getLikes();
            TransactionName = article.getTransaction().getMessage();
        }
        public static class CommentDTO{
            private String userId;
            private String commentContent;
            private LocalDateTime createDate;
            private LocalDateTime modifiedDate;

            public CommentDTO(Comment comment) {
                this.userId = comment.getUser().getLoginId();
                this.commentContent = comment.getContent();
                this.createDate = comment.getCreateDate();
                this.modifiedDate = comment.getModifiedDate();
            }
        }

    }

}
