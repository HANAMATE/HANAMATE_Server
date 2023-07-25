package team.hanaro.hanamate.domain.Login;

import lombok.*;
import team.hanaro.hanamate.domain.Member.MemberEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoginReq {

    private String memberEmail;
    private String memberPassword;

    public static LoginReq toMemberDTO(MemberEntity memberEntity) {
        LoginReq loginReq = new LoginReq();
        loginReq.setMemberEmail(memberEntity.getMemberEmail());
        loginReq.setMemberPassword(memberEntity.getMemberPassword());
        return loginReq;
    }
}
