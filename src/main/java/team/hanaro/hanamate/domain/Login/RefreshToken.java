package team.hanaro.hanamate.domain.Login;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    //로그인 성공할 시 Refresh 토큰을 DB에 저장을 위한 Domain
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String loginId;

    public RefreshToken(String token, String loginId) {
        this.refreshToken = token;
        this.loginId = loginId;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}