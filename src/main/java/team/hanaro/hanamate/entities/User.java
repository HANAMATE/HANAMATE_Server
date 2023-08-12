package team.hanaro.hanamate.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.hanaro.hanamate.domain.User.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //TEST 용도 Setter 사용
@Entity
@Table(name = "Users")
public class User extends BaseTime implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idx;
    
    //xxToOne 관계는 모두 FetchType.LAZY를 걸어줘야 함.
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "my_wallet_id")
    private MyWallet myWallet;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(name="login_id",unique = true)
    private String id;

    @Column
    private String password;

    @Column
    private String name;

    // TODO: 2023/08/09 identification -> rrn 으로 변수명 변경 요청 
    @Column
    private String identification;

    @Column
    private String phoneNumber;

    @Column
    private UserType userType;

    //SpringSecurity 관련
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
