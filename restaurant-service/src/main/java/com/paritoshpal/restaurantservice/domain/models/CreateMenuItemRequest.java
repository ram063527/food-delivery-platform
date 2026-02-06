package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.DietaryInfo;
import com.paritoshpal.restaurantservice.domain.MenuCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateMenuItemRequest(
        @NotBlank String name,
        String description,
        @NotNull @Positive BigDecimal price,
        @NotNull MenuCategory category,
        boolean availability,
        String imageUrl,
        @NotNull DietaryInfo dietaryInfo
) {
}
