package com.akrima.hotel.configuration;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import io.swagger.v3.oas.models.info.Info;

/**
 * @Author Abderrahim KRIMA
 */
@Configuration
public class OpenApiConfiguration implements WebFluxConfigurer {

    @Bean
    public GroupedOpenApi userOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/users/**" };
        return GroupedOpenApi.builder().group("users")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi roomOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/rooms/**" };
        return GroupedOpenApi.builder().group("rooms")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Room API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi reservationOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/reservations/**" };
        return GroupedOpenApi.builder().group("reservations")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Reservation API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }
}

