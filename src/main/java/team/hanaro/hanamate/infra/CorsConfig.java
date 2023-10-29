package team.hanaro.hanamate.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all paths.
                .allowedOrigins("http://localhost:3000", "http://localhost:80","http://localhost:3001", "http://hanamate-front.s3-website.ap-northeast-2.amazonaws.com", "https://front.hana-kdt.co.kr"
                        ,"http://15.165.150.155:80", "http://15.165.150.155:3000", "http://15.165.150.155")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "X-Refresh-Token") // 클라이언트에 노출할 헤더 설정
                .allowCredentials(true); // 인증 정보 허용 설정

    }
}
