package team.hanaro.hanamate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.hanaro.hanamate.domain.Login.LoginService;
import team.hanaro.hanamate.domain.Member.MemberType;
import team.hanaro.hanamate.jwt.JwtTokenFilter;
import team.hanaro.hanamate.jwt.JwtTokenUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //회원가입
    private final JwtTokenUtil jwtTokenUtil;

    //회원가입시 비밀번호를 암호화를 진행해야 한다. 따라서 컨테이너에 PasswordEncoder를 Bean으로 등록해야 한다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer ignoringCustomizer(){
        return (web) -> web.ignoring().antMatchers("/console/**");
    }


    //로그인
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
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.cors()
//                .and()
//                .httpBasic().disable()
//                .csrf().disable() //csrf를 비활성화하여 Post 요청이 가능하도록 한다. csrf는 특정 웹사이트에 공격 방법 중 하나이고, 여기서 설정하는 csrf는 이를 방지하기 위해 csrf 토큰을 통해 인증을 하는 것이다.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Token 로그인 방식에서는 session이 필요없기 때문
//                .and()
//                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
//                .and()
//                .antMatchers("/account/**").permitAll() //회원가입과 로그인을 위한 /account/**로 들어오는 요청은 전부 검증없이 요청을 허용하도록 설정하였다.
//                .anyRequest().authenticated()
//                .antMatchers("/info").authenticated()//.authenticated():해당 URL에 진입하기 위해서 Authentication(인증, 로그인)이 필요함
//                .antMatchers("/admin/**").hasAuthority(MemberType.parent.name())//.hasAuthority(): 해당 URL에 진입하기 위해서 Authorization(인가, ex)권한이 parent인 저만 진입 가능)이 필요함
//                .and().build();
//    }
}