package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalTime;

public record CreateRestaurantRequest(

        @NotEmpty(message = "Restaurant name cannot be empty")
        String name,
        String description,
        @NotEmpty(message = "Restaurant phone cannot be empty")
        String phone,
        @NotEmpty(message = "Restaurant email cannot be empty")
        @Email
        String email,
        @NotNull(message = "Restaurant owner ID cannot be null")
        Long ownerId,
        String cuisine,
        @NotNull(message = "Restaurant status cannot be null")
        Status status,
        BigDecimal rating,
        LocalTime openingTime,
        LocalTime closingTime,
        @PositiveOrZero(message = "Delivery fee cannot be negative")
        @NotNull(message = "Delivery fee cannot be null")
        BigDecimal deliveryFee,
        @PositiveOrZero(message = "Minimum order amount cannot be negative")
        @NotNull(message = "Minimum order amount cannot be null")
        BigDecimal minimumOrderAmount,
        @PositiveOrZero(message = "Estimated delivery time cannot be negative")
        @NotNull(message = "Estimated delivery time cannot be null")
        Integer estimatedDeliveryTime
) {
}
