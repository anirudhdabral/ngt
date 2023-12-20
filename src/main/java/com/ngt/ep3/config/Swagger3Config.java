package com.ngt.ep3.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "NGT Episode3", version = "1.0",
        license=@License(name="Nagarro All Right Reserved")
))
public class Swagger3Config {
    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI();
    }
}
