package team.hanaro.hanamate.domain.Jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.hanaro.hanamate.domain.Login.LoginService;
import team.hanaro.hanamate.domain.Member.MemberType;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final MemberService memberService;
    private final LoginService loginService;
    private static String secretKey = "vlweyvbsyt9v7zq57tejmnvuyzblycfpqye08f7mgva9xkha3";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Token 로그인 방식에서는 session이 필요없기 때문
                .and()
                .addFilterBefore(new JwtTokenFilter(loginService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()//인증, 인가가 필요한 URL 지정
                .antMatchers("/info").authenticated()//.authenticated():해당 URL에 진입하기 위해서 Authentication(인증, 로그인)이 필요함
                .antMatchers("/admin/**").hasAuthority(MemberType.parent.name())//.hasAuthority(): 해당 URL에 진입하기 위해서 Authorization(인가, ex)권한이 parent인 저만 진입 가능)이 필요함
                .and().build();
    }
}