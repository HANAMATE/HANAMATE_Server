package team.hanaro.hanamate.domain.Login;

import lombok.*;
import team.hanaro.hanamate.domain.Member.MemberEntity;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoginReq {

    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPw;

    public static LoginReq toMemberDTO(MemberEntity memberEntity) {
        LoginReq loginReq = new LoginReq();
        loginReq.setLoginId(memberEntity.getLoginId());
        loginReq.setLoginPw(memberEntity.getLoginPw());
        return loginReq;
    }
}
