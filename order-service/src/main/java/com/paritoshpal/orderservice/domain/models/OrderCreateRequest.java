package com.paritoshpal.orderservice.domain.models;

import com.paritoshpal.orderservice.domain.orderItem.OrderItemEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
