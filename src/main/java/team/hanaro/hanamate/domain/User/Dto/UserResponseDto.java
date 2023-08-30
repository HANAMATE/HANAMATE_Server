package team.hanaro.hanamate.domain.User.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import team.hanaro.hanamate.entities.Child;
import team.hanaro.hanamate.entities.ParentAndChild;
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
        private String userType;
        private String userName;
        private Long myWalletId;


        public UserInfo(User user) {
            userId = user.getLoginId();
            userType= user.getUserType();
            userName = user.getName();
            myWalletId= user.getMyWallet().getId();

        }
    }

    @SuperBuilder
    @Data
    public static class ParentAndChildResponseDTO{
        private String parentId;
        private String parentName;
        private String childId;
        private String childName;

        public ParentAndChildResponseDTO(ParentAndChild parentAndChild) {
            this.parentId = parentAndChild.getParent().getLoginId();
            this.parentName = parentAndChild.getParent().getUsername();
            this.childId = parentAndChild.getChild().getLoginId();
            this.childName = parentAndChild.getChild().getUsername();
        }
    }

    @Data
    public static class findMyListDTO<T extends User> {
        private Long userIdx;
        private String userId;
        private String name;
        private Long walletId;

        public findMyListDTO(T user) {
            userIdx = user.getIdx();
            userId= user.getLoginId();
            name = user.getName();
            walletId = user.getMyWallet().getId();
        }
    }

}
