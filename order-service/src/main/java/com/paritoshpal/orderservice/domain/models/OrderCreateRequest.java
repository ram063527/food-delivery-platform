package com.paritoshpal.orderservice.domain.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequest(

        @NotNull(message = "Customer ID cannot be null")
        Long restaurantId,
        @NotNull(message = "Customer ID cannot be null")
        Long deliveryAddressId,
        @NotEmpty(message = "Order must contain at least one item")
        List<OrderItemCreateRequest> orderItems,
        String specialInstructions

){
}
