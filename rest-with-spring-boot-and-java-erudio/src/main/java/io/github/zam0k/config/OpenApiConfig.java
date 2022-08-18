package io.github.zam0k.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTFUL API with JAVA 18 Spring Boot 3")
                        .version("v1")
                        .description("Some description about your API")
                        .termsOfService("https://google.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://google.com"))
                        );
    }
}

