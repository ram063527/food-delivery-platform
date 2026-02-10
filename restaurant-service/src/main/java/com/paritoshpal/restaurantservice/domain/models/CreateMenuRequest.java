package com.paritoshpal.restaurantservice.domain.models;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CreateMenuRequest(

        @NotBlank(message = "Menu name cannot be empty")
        String name,

        String description,

        List<CreateMenuItemRequest> items
) {
}
