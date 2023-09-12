package com.shj.onlinememospringproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {  // 참고로 Swagger에서 test돌릴때에 JWT 사용시, Authorize값에 'Bearer ${accessToken}'으로 넣어주면 된다.

    @Bean
    @Profile({"devswagger && !prodswagger"})  // 만약 로컬환경일경우 swagger 활성화
    public Docket restAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shj.onlinememospringproject.controller"))
                .paths(PathSelectors.any())
                .build()

                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    @Bean
    @Profile({"prodswagger && !devswagger"})  // 만약 운영환경일경우 swagger 비활성화
    public Docket disable() {
        return new Docket(DocumentationType.SWAGGER_2).enable(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("OnlineMemo REST API")
                .version("1.0.0")
                .description("온라인메모장의 api 문서입니다.")
                .build();
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}