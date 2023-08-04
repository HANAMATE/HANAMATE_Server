package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "member")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private Long walletId; /* 개인 지갑 Id */
    private Long accountId;

    private String memberName;
    private String loginId;

    private String loginPw;

    private String salt;

    private String identification;

    private String phoneNumber;

    private Integer age;
    private Timestamp registrationDate;

    private boolean memberType; /* 0: 부모, 1: 아이 */

}
