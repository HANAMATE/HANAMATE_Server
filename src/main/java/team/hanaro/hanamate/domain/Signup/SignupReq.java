package team.hanaro.hanamate.domain.Signup;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupReq {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
}
