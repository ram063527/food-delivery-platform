package com.paritoshpal.restaurantservice.domain.models;


import com.paritoshpal.restaurantservice.domain.Status;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalTime;

public record RestaurantUpdateRequest(
        String name,
        String description,
        String phone,
        String cuisine,
        Status status,
        LocalTime openingTime,
        LocalTime closingTime,
        @PositiveOrZero
        BigDecimal deliveryFee,
        @PositiveOrZero
        BigDecimal minimumOrderAmount,
        @PositiveOrZero
        Integer estimatedDeliveryTime
) {

}
