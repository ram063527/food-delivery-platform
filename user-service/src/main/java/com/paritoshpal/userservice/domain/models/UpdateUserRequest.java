package com.paritoshpal.userservice.domain.models;

public record UpdateUserRequest(

        String firstName,
        String lastName,
        String phone
) {
}
