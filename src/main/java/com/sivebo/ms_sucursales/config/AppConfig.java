package com.sivebo.ms_sucursales.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

        @Value("${ms.regiones.url}")
        private String comunasBaseUrl;
        

        @Bean
        public WebClient.Builder webClientBuilder() {
                return WebClient.builder();
        }

        @Bean
        public WebClient regionesWebClient(WebClient.Builder builder) {
                return builder
                        .baseUrl(comunasBaseUrl)
                        .build();
        }
}
