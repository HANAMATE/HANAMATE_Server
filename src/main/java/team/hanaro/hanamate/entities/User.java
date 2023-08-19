package team.hanaro.hanamate.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //TEST 용도 Setter 사용
@Entity
@Table(name = "Users")
@DiscriminatorColumn(name = "userType")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends BaseTime implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idx;

    //xxToOne 관계는 모두 FetchType.LAZY를 걸어줘야 함.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "my_wallet_id")
    private MyWallet myWallet;

    @Builder.Default
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoimWalletAndUser> moimWalletAndUsers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(name = "login_id", unique = true)
    private String loginId;

    @Column
    private String password;

    @Column
    private String name;

    // TODO: 2023/08/09 identification -> rrn 으로 변수명 변경 요청 
    @Column
    private String identification;

    @Column
    private String phoneNumber;

    @Transient
    public String getUserType(){
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }


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
        return loginId;
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
