//package team.hanaro.hanamate.domain.Login;
//
//import lombok.*;
//import team.hanaro.hanamate.domain.Member.MemberEntity;
//import team.hanaro.hanamate.domain.Member.MemberType;
//
//import javax.validation.constraints.NotBlank;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class LoginRes {
//    @NotBlank
//    private String loginId;
//    @NotBlank
//    private String loginPw;
//    private MemberType memberType; /* 0: 부모, 1: 아이 */
//
//    public static LoginRes toMemberDTO(MemberEntity memberEntity) {
//        LoginRes loginRes = new LoginRes();
//        loginRes.setLoginId(memberEntity.getLoginId());
//        loginRes.setLoginPw(memberEntity.getLoginPw());
//        loginRes.setMemberType(memberEntity.getMemberType());
//        return loginRes;
//    }
//
//}
