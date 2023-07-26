package team.hanaro.hanamate.domain.Login;

import lombok.*;
import team.hanaro.hanamate.domain.Member.MemberEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoginReq {

    private String loginId;
    private String loginPw;

    public static LoginReq toMemberDTO(MemberEntity memberEntity) {
        LoginReq loginReq = new LoginReq();
        loginReq.setLoginId(memberEntity.getLoginId());
        loginReq.setLoginPw(memberEntity.getLoginPw());
        return loginReq;
    }
}
