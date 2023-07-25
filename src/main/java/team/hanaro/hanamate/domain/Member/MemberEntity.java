package team.hanaro.hanamate.domain.Member;

import lombok.Builder;
import lombok.Getter;
import team.hanaro.hanamate.domain.Signup.SignupReq;

import javax.persistence.*;

@Entity
//@Setter
@Getter
@Table(name = "member_table")
//테이블 역할을 함.DB 테이블을 자바 객체처럼 쓰는 것
public class MemberEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public MemberEntity() {

    }

    @Builder
    public MemberEntity(Long id, String memberEmail, String memberPassword, String memberName) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
    }

    public static MemberEntity toMemberEntity(SignupReq signupReq) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail(signupReq.getMemberEmail())
                .memberPassword(signupReq.getMemberPassword())
                .memberName(signupReq.getMemberName())
                .build();

        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.builder()
                .id(memberDTO.getId())
                .memberEmail(memberDTO.getMemberEmail())
                .memberPassword(memberDTO.getMemberPassword())
                .memberName(memberDTO.getMemberName())
                .build();
        return memberEntity;
    }


}