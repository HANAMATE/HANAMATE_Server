package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import team.hanaro.hanamate.entities.Article;
import team.hanaro.hanamate.entities.Comment;
import team.hanaro.hanamate.entities.Images;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        Long commentId;
        String userId;
        String userName;
        String content;

        public WriteCommentResponseDTO(Comment comment) {
            articleId = comment.getArticle().getId();
            commentId = comment.getCommentId();
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
        private List<String> fileName;
        private List<String> imageUrl;
        private String title;
        private String content;
        private Long like;

        public void addCommentList(List<CommentResponseDTO> commentResponseDTOList){
            this.commentList = commentResponseDTOList;
        }
        public ArticleResponseDTO(Article article, boolean imgCheck) {
            articleId = article.getId();
            //n+1터지겠다 무조건 서비스단에서 빼서 처리
            if(imgCheck==true){
                this.fileName = article.getImagesList().stream().map(Images::getSavedName).collect(Collectors.toList());
                this.imageUrl = article.getImagesList().stream()
                        .map(Images::getSavedPath).collect(Collectors.toList());
            }
            title = article.getTitle();
            content = article.getContent();
            like = article.getLikes();
            transactionMessage = article.getTransaction().getMessage();
        }
        public ArticleResponseDTO(Article article) {
            articleId = article.getId();
            //n+1터지겠다 무조건 서비스단에서 빼서 처리
                this.fileName = article.getImagesList().stream().map(Images::getSavedName).collect(Collectors.toList());
                this.imageUrl = article.getImagesList().stream()
                        .map(Images::getSavedPath).collect(Collectors.toList());
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
        private String writerName;
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
            this.writerName = comment.getWriterName();
        }
    }
}
