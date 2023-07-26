package team.hanaro.hanamate.domain.Member;

import lombok.*;
import team.hanaro.hanamate.domain.Signup.SignupReq;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "members")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//테이블 역할을 함.DB 테이블을 자바 객체처럼 쓰는 것
public class MemberEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long memberId;

    private Long walletId; /* 개인 지갑 Id */
    private Long accountId;

    private String memberName;
    private String loginId;

    private String loginPw;

    private String salt;

    private String phoneNumber;

    private String birth;
    private Timestamp registrationDate;

    private boolean memberType; /* 0: 부모, 1: 아이 */



    public static MemberEntity toMemberEntity(SignupReq signupReq) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberName(signupReq.getMemberName())
                .phoneNumber(signupReq.getPhoneNumber())
                .loginId(signupReq.getLoginId())
                .loginPw(signupReq.getLoginPw())
                .birth(signupReq.getBirth())
                .build();
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberName(memberDTO.getMemberName())
                .phoneNumber(memberDTO.getPhoneNumber())
                .loginId(memberDTO.getLoginId())
                .loginPw(memberDTO.getLoginPw())
                .birth(memberDTO.getBirth())
                .build();
        return memberEntity;
    }


}