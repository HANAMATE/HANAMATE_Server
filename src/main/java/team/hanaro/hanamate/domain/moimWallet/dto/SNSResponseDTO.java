package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import team.hanaro.hanamate.entities.Article;
import team.hanaro.hanamate.entities.Comment;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

public class SNSResponseDTO {

    @NoArgsConstructor
    @Data
    public static class AddLikeResponseDTO {
        Long articleId;
        Long likes;
        public AddLikeResponseDTO(Article article) {
            articleId = article.getId();
            likes = article.getLikes();
        }
    }

    @Data
    @NoArgsConstructor
    public static class WriteCommentResponseDTO {
        Long articleId;
        String userId;
        String userName;
        String content;

        public WriteCommentResponseDTO(Comment comment) {
            articleId = comment.getArticle().getId();
            userId = comment.getUser().getLoginId();
            userName = comment.getUser().getName();
            content = comment.getContent();
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
}
