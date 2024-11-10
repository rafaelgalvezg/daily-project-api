package dev.rafaelgalvezg.dailyprojectapi.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API DAILY PROJECT")
                        .version("1.0.0")
                        .description("Documentation of the API for the Daily Project application"));
    }


}
