package team.hanaro.hanamate.domain.User.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.hanaro.hanamate.entities.User;

public class UserResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserInfo {
        private String userId;

        public UserInfo(User user) {
            userId = user.getLoginId();
        }
    }

}
