package com.paritoshpal.restaurantservice.clients.user;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);
    private final RestClient restClient;


    @Retry(name = "user-service")
    public Optional<UserResponse> getUserById(Long userId){
       log.info("Fetching user for id: {} from User Service", userId);
        try {
            // We do NOT use onStatus here if we want to catch the exception manually
            UserResponse userBody = restClient
                    .get()
                    .uri("/api/users/id/{id}", userId)
                    .retrieve()
                    .body(UserResponse.class);

            return Optional.ofNullable(userBody);
        } catch (HttpClientErrorException.NotFound ex) {
            // This catch block specifically handles the 404 from User Service
            log.warn("User with id: {} was not found in User Service.", userId);
            return Optional.empty();
        }
        // Note: Other exceptions like ResourceAccessException (Timeout) or
        // HttpClientErrorException.Conflict (409) will bubble up as 500s, which is correct.
    }





}
