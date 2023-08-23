package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SNSRequestDTO {

    @Data
    public static class AddLikeRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long articleId;
//        @NotEmpty(message = "유저 아이디는 반드시 입력되어야 합니다.")
//        String userId;
    }
    @Data
    public static class WriteCommentRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long articleId;
        @NotEmpty(message = "유저 아이디는 반드시 입력되어야 합니다.")
        String userId;
        @NotEmpty(message = "공백은 허용하지 않으며 한글자 이상 입력되어야 합니다.")
        String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class WriteArticleRequestDTO {
        @NotNull
        private Long transactionId;
        @NotBlank
        private String title;
        @NotBlank
        private String content;

    }

    @Data
    public static class GetOrDeleteArticleRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long articleId;
    }
    @Data
    public static class DeleteCommentRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long commentId;
    }
    @Data
    public static class UpdateCommentRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long commentId;
        @NotEmpty(message = "공백은 허용하지 않으며 한글자 이상 입력되어야 합니다.")
        String content;
    }
    @Data
    public class ImageUploadDTO {

        private List<MultipartFile> files;
    }

}
