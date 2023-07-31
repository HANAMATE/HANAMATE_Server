//package team.hanaro.hanamate.domain.Login;
//
//import lombok.*;
//import team.hanaro.hanamate.domain.Member.MemberType;
//
//import javax.validation.constraints.NotBlank;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//
//public class LoginReq {
//
//    @NotBlank
//    private String loginId;
//    @NotBlank
//    private String loginPw;
//    private MemberType memberType; /* 0: 부모, 1: 아이 */
//
////    public static LoginReq toMemberDTO(MemberEntity memberEntity) {
////        LoginReq loginReq = new LoginReq();
////        loginReq.setLoginId(memberEntity.getLoginId());
////        loginReq.setLoginPw(memberEntity.getLoginPw());
////        loginReq.setMemberType(memberEntity.getMemberType());
////        return loginReq;
////    }
//
//    public void setEncodePwd(String encodePwd){
//        this.loginPw=encodePwd;
//    }
//}
