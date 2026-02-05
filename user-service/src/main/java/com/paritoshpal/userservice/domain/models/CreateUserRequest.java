package com.paritoshpal.userservice.domain.models;

import com.paritoshpal.userservice.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(

        @Email
        @NotBlank(message = "Email cannot be empty")
        String email,
        @NotBlank(message = "First name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        String phone,
        @NotBlank(message = "Password cannot be empty")
        String password,

        @NotNull(message = "Role cannot be null")
        Role role
) {
}
