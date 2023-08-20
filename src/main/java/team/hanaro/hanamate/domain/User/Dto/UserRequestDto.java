package team.hanaro.hanamate.domain.User.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import team.hanaro.hanamate.domain.User.UserType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRequestDto {

    @Getter
    @Setter

    public static class SignUp {

        @NotEmpty(message = "이름은 필수 입력값입니다.")
        @Pattern(regexp = "^[가-힣]{2,4}$", message = "이름은 2~4자 이내입니다.")
        private String name;


        @NotEmpty(message = "아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9]{2,6}$", message = "아이디는 영문 대 소문자, 숫자만 사용할 수 있습니다. ")
        private String id;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotEmpty(message = "주민등록번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$", message = "주민등록번호 형식에 맞게 입력해주세요.")
        private String identification;

        @NotEmpty(message = "전화번호는 필수 입력값입니다.")
        @Pattern(regexp = "^0\\d{1,2}(-|\\))\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요")
        private String phoneNumber;

        private UserType userType;




    }


    @Getter
    @Setter
    public static class Login {
        @NotEmpty(message = "아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9]{2,6}$", message = "아이디 형식에 맞지 않습니다.")
        private String id;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(id, password);
        }
    }
    @Getter
    @Setter
    public static class Reissue {

    }
    @Getter
    @Setter
    public static class Init {

    }

    @Getter
    @Setter
    public static class Logout {
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }

    @Data
    public static class FindUserRequestDTO{
        @NotEmpty(message = "아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9]{2,6}$", message = "아이디는 영문 대 소문자, 숫자만 사용할 수 있습니다. ")
        private String requesterId;

        @NotEmpty(message = "전화번호는 필수 입력값입니다.")
        @Pattern(regexp = "^0\\d{1,2}(-|\\))\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요")
        private String phoneNumber;
    }

    @Data
    public static class ParentAddOrDeleteChildRequestDTO {
        @NotEmpty(message = "요청자 아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9]{2,6}$", message = "아이디는 영문 대 소문자, 숫자만 사용할 수 있습니다. ")
        private String requesterId;

        @NotEmpty(message = "추가 대상 아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9]{2,6}$", message = "아이디는 영문 대 소문자, 숫자만 사용할 수 있습니다. ")
        private String targetId;
    }
    @Data
    public static class getMyChildOrParentRequestDTO{
        @NotEmpty(message = "요청자 아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9]{2,6}$", message = "아이디는 영문 대 소문자, 숫자만 사용할 수 있습니다. ")
        private String userId;
    }

}
