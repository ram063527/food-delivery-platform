package com.paritoshpal.orderservice.domain.models;

import com.paritoshpal.orderservice.domain.OrderStatus;

public record OrderResponse(
        Long id,
        String orderNumber,
        OrderStatus status

) {
}
