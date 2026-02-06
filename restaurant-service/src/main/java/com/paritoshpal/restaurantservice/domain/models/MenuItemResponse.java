package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.DietaryInfo;
import com.paritoshpal.restaurantservice.domain.MenuCategory;

import java.math.BigDecimal;

public record MenuItemResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        MenuCategory category,
        boolean availability,
        String imageUrl,
        DietaryInfo dietaryInfo
) {
}
