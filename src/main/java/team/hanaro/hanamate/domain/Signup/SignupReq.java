package team.hanaro.hanamate.domain.Signup;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupReq {
    private String memberName;
    private String phoneNumber;
    private String loginId;
    private String loginPw;
    private String birth;
}
