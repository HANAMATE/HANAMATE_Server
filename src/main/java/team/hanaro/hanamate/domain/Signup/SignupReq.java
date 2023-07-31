//package team.hanaro.hanamate.domain.Signup;
//
//import lombok.*;
//import team.hanaro.hanamate.domain.Member.MemberEntity;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class SignupReq {
//    private String memberName;
//    private String phoneNumber;
//    private String loginId;
//    private String loginPw;
//    private String birth;
//
//
//    public MemberEntity toMemberEntity() {
//        MemberEntity memberEntity = MemberEntity.builder()
//                .memberName(this.getMemberName())
//                .phoneNumber(this.getPhoneNumber())
//                .loginId(this.getLoginId())
//                .loginPw(this.getLoginPw())
//                .birth(this.getBirth())
////                .memberType(MemberType.parent)
//                .build();
//        return memberEntity;
//    }
//
////    //비밀번호 암호화
////    public MemberEntity toMemberEntity(String encodedPassword) {
////        MemberEntity memberEntity = MemberEntity.builder()
////                .memberName(this.getMemberName())
////                .phoneNumber(this.getPhoneNumber())
////                .loginId(this.getLoginId())
////                .loginPw(encodedPassword)
////                .birth(this.getBirth())
//////                .memberType(MemberType.parent)
////                .build();
////        return memberEntity;
////    }
//
//
//
//
//}
