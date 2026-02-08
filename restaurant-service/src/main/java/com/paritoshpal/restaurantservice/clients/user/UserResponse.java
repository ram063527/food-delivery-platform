package com.paritoshpal.restaurantservice.clients.user;


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
