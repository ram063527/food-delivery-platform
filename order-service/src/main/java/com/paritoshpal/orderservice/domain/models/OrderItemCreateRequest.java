package com.paritoshpal.orderservice.domain.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record OrderItemCreateRequest(

        @NotNull(message = "Menu Item ID cannot be null")
        Long menuItemId,
        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity,
        String itemSpecialInstructions
) {
}

