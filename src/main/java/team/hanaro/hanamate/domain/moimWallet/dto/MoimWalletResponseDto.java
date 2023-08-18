package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import team.hanaro.hanamate.entities.Article;
import team.hanaro.hanamate.entities.Comment;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.Transactions;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @SuperBuilder
    @Data
    public static class CommentResponseDTO {
        private Long commentId;
        private Long userIdx;
        private String writerId;
        private String commentContent;
        private LocalDateTime createDate;
        private LocalDateTime modifiedDate;

        public CommentResponseDTO(Comment comment) {
            this.commentId = comment.getCommentId();
            this.userIdx = comment.getUser().getIdx();
            this.writerId = comment.getUser().getLoginId();
            this.commentContent = comment.getContent();
            this.createDate = comment.getCreateDate();
            this.modifiedDate = comment.getModifiedDate();
        }
    }

    @NoArgsConstructor
    @Data
    public static class ArticleResponseDTO {
        private String transactionMessage;
        private Long articleId;
        private List<CommentResponseDTO> commentList;
        private byte[] imageId;
        private String title;
        private String content;
        private Long like;

        public void addCommentList(List<CommentResponseDTO> commentResponseDTOList){
            this.commentList = commentResponseDTOList;
        }
        public ArticleResponseDTO(Article article) {
            articleId = article.getId();
            imageId = article.getImageId();
            title = article.getTitle();
            content = article.getContent();
            like = article.getLikes();
            transactionMessage = article.getTransaction().getMessage();
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
        //내가 가입한 모든 모임 월렛 조회용
//        public satatic class MoimWalletList(MoimWallet wallets) {
//            walletId = wallets.getId();
//            walletName = wallets.getWalletName();
//            walletType = wallets.getDecriminatorValue();
//            balance = wallets.getBalance();
//            if ("moim".equals(walletType)) {
//                targetAmount = ((MoimWallet) wallets).getTargetAmount();
//            }
//        }

}
