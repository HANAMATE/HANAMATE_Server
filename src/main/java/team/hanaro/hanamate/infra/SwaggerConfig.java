package team.hanaro.hanamate.infra;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import team.hanaro.hanamate.domain.moimWallet.SNSController;

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
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .useDefaultResponseMessages(false)
                .select()

                .apis(RequestHandlerSelectors
                        .basePackage("team.hanaro.hanamate"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(SNSController.class);
    }
}
