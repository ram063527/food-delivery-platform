package com.paritoshpal.restaurantservice.clients.user;

import com.paritoshpal.restaurantservice.ApplicationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class UserServiceClientConfiguration {


    // Rest Client Bean
    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties applicationProperties){
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofSeconds(5));
                    customizer.setReadTimeout(Duration.ofSeconds(5));
                })
                .build();


        return builder
                .baseUrl(applicationProperties.userServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
