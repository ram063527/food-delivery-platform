package com.paritoshpal.restaurantservice.domain.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateMenuRequest(

        @NotNull(message = "Restaurant ID cannot be null")
        Long restaurantId,

        @NotBlank(message = "Menu name cannot be empty")
        String name,

        String description,

        List<CreateMenuItemRequest> items
) {
}
