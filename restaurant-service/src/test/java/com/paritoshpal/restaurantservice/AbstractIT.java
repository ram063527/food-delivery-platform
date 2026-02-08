package com.paritoshpal.restaurantservice;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {

    @LocalServerPort
    int port;

    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

    @BeforeAll
    static void beforeAll() {
        wiremockServer.start();
        configureFor(wiremockServer.getHost(), wiremockServer.getPort());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("restaurant.user-service-url", wiremockServer::getBaseUrl);
    }


    @BeforeEach
    void setUp() {
        System.setProperty("server.port", String.valueOf(port));
        RestAssured.port = port;
    }

    protected static void mockGetUserById(Long userId){
        stubFor(WireMock.get(urlEqualTo("/api/users/id/"+userId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(
                        """
                        {
                            "id": %d,
                            "role": "RESTAURANT_OWNER",
                            "email": "owner@test.com"
                        }
                        """.formatted(userId) // Correctly use the placeholder
                )));
    }

    
}
