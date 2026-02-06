package com.paritoshpal.userservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paritoshpal.userservice.config.SecurityConfig;
import com.paritoshpal.userservice.domain.UserService;
import com.paritoshpal.userservice.domain.models.CreateUserRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

import static com.paritoshpal.userservice.testdata.TestDataFactory.*;
import static org.junit.jupiter.api.Named.named;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerUnitTests  {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest(name = "[{index}] - {0}")
    @MethodSource("createUserRequestProvider")
    void shouldReturnBadRequestWhenCreateUserPayloadIsInvalid(CreateUserRequest request) throws Exception {

        Mockito.when(userService.createUser(any(CreateUserRequest.class))).thenReturn(null);

        mockMvc.perform(
                post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    static Stream<Arguments> createUserRequestProvider() {
        return Stream.of(
                Arguments.arguments(named("User with invalid email", createUserRequestWithInvalidEmail())),
                Arguments.arguments(named("User without first name", createUserRequestWithoutFirstName())),
                Arguments.arguments(named("User without last name", createUserRequestWithoutLastName())),
                Arguments.arguments(named("User without password", createUserRequestWithoutPassword())),
                Arguments.arguments(named("User with invalid role", createUserRequestWithInvalidRole()))
        );
    }


}
