package team.hanaro.hanamate.domain.moimWallet.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SNSRequestDTO {

    @Data
    public static class AddLikeRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long articleId;
        @NotEmpty(message = "유저 아이디는 반드시 입력되어야 합니다.")
        String userId;
        @NotEmpty(message = "유저 이름은 반드시 입력되어야 합니다.")
        String userName;
    }
    @Data
    public static class WriteCommentRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long articleId;
        @NotEmpty(message = "유저 아이디는 반드시 입력되어야 합니다.")
        String userId;
        @NotEmpty(message = "유저 이름은 반드시 입력되어야 합니다.")
        String userName;
        @NotEmpty(message = "공백은 허용하지 않으며 한글자 이상 입력되어야 합니다.")
        String content;
    }

    @Data
    public static class GetArticleRequestDTO {
        @NotNull(message = "글 번호는 반드시 입력되어야 합니다.")
        Long articleId;
    }
}
