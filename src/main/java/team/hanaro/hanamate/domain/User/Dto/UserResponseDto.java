package team.hanaro.hanamate.domain.User.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.hanaro.hanamate.domain.User.UserType;
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
        private UserType userType;

        private String userName;
        private Long myWalletId;


        public UserInfo(User user) {
            userId = user.getLoginId();
            userType= user.getUserType();
            userName = user.getName();
            myWalletId= user.getMyWallet().getId();

        }
    }


}
