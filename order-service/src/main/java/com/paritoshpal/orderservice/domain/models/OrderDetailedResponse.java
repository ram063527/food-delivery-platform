package com.paritoshpal.orderservice.domain.models;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailedResponse(
        Long id,
        Long orderNumber,
        List<OrderItemResponse> orderItems,
        Long restaurantId, // For Front end to fetch the restaurant details, using another endpoint
        Long deliveryAddressId, // For Front end to fetch the address details, using another endpoint
        BigDecimal totalAmount,
        BigDecimal deliveryFee,
        BigDecimal discount,
        BigDecimal tax
) {
}
