package com.paritoshpal.userservice.domain.models;

import com.paritoshpal.userservice.domain.Role;
import com.paritoshpal.userservice.domain.Status;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Role role,
        Status status,
        LocalDateTime createdAt
) {
}
