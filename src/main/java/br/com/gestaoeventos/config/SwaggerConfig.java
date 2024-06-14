package br.com.gestaoeventos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Projeto Pass-in")
                .description("API REST responsável por realizar o controle de eventos, participantes e check-ins.")
                .version("v1.0"));
    }
}
