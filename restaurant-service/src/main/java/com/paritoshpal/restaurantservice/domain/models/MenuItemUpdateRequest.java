package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.DietaryInfo;
import com.paritoshpal.restaurantservice.domain.MenuCategory;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MenuItemUpdateRequest(
        String name,
        String description,

        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        MenuCategory category,
        DietaryInfo dietaryInfo,
        Boolean availability,
        String imageUrl
) {
}
