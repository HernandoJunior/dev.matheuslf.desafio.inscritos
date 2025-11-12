package dev.matheuslf.desafio.inscritos.config.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.awt.SystemColor.info;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Projeto Inscritos Prof. Matheus")
                .version("1.0.0")
                .description("Documentaçao do projeto.")
        );
    }
}