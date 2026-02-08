package com.paritoshpal.restaurantservice.clients.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);
    private final RestClient restClient;


    public Optional<UserResponse> getUserById(Long userId){
       log.info("Fetching user for id: {} from User Service", userId);
        try {
            UserResponse userBody = restClient
                    .get()
                    .uri("/api/users/{id}", userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("User with id: {} not found in User Service.", userId);
                    })
                    .body(UserResponse.class);
            return Optional.ofNullable(userBody);
        } catch (Exception e) {
            log.error("Error occurred while fetching user with id: {} from User Service. Error: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}
