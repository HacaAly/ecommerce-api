package com.hikadobushido.ecommerce_java.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer",
                        new SecurityScheme().type(Type.HTTP).scheme("Bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Ecommerce API")
                        .version("1.0")
                        .description("Aplikasi ecommerce")
                );
    }
}