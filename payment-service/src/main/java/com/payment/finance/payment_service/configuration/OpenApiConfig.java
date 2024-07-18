package com.payment.finance.payment_service.configuration;

import io.swagger.v3.oas.models.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.models.info.Info;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Your API Title")
                                 .description("Your API Description")
                                 .version("1.0"));
    }
}