package team.hanaro.hanamate.infra;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@OpenAPIDefinition(
        info = @Info(title = "하나메이트",
                description = "서비스 API 명세서",
                version = "v1"))
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any()) // 모든 컨트롤러를 문서화하려면 이렇게 설정
//                .paths(PathSelectors.any()) // 모든 엔드포인트를 문서화하려면 이렇게 설정
                .build();
    }
}
