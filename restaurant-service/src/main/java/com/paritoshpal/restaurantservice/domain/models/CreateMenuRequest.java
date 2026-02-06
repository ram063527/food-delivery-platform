package com.paritoshpal.restaurantservice.domain.models;

import jakarta.validation.constraints.NotBlank;

public record CreateMenuRequest(
        @NotBlank String name,
        String description
) {
}
