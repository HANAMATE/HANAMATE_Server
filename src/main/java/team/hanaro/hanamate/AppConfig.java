package team.hanaro.hanamate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.hanaro.hanamate.domain.member.MemberRepository;
import team.hanaro.hanamate.domain.member.MemberRepositoryImpl;
import team.hanaro.hanamate.domain.member.MemberService;
import team.hanaro.hanamate.domain.member.MemberServiceImpl;

@Configuration
public class AppConfig {

//    @Bean
//    public MemberService memberService(){
//        return new MemberServiceImpl(memberRepository());
//    }
//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemberRepositoryImpl();
//    }

}