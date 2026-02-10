package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.DietaryInfo;
import com.paritoshpal.restaurantservice.domain.MenuCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateMenuItemRequest(

        @NotEmpty(message = "Name cannot be empty")
        String name,
        String description,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,
        @NotNull(message = "Category cannot be null")
        MenuCategory category,
        boolean availability,
        String imageUrl,
        @NotNull(message = "Dietary info cannot be null")
        DietaryInfo dietaryInfo
) {
}
