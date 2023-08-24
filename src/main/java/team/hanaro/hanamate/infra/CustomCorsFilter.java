package team.hanaro.hanamate.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CustomCorsFilter extends CorsFilter {

    public CustomCorsFilter() {
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:3000"); // 허용할 도메인을 설정하세요
//        corsConfig.addAllowedOrigin("/**"); // 특정 도메인을 입력하면 해당 도메인에서 온 요청만 허용합니다.
        corsConfig.addAllowedHeader("/**");
        corsConfig.addAllowedMethod("/**");
        corsConfig.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
