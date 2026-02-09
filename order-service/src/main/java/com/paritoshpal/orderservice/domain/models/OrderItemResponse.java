package com.paritoshpal.orderservice.domain.models;

import java.math.BigDecimal;

public record OrderItemResponse(

        Long id,
        Long menuItemId,  // For front end to fetch the menu item details, using another endpoint
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal
) {
}
