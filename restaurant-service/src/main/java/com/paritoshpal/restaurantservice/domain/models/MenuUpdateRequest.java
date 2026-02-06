package com.paritoshpal.restaurantservice.domain.models;

public record MenuUpdateRequest(
        String name,
        String description
) {
}
