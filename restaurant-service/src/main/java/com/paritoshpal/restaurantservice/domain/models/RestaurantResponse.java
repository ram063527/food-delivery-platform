package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.Status;

import java.math.BigDecimal;


public record RestaurantResponse(
        Long id,
        String name,
        String description,
        String cuisine,
        BigDecimal rating,
        Status status,
        RestaurantAddressResponse address
) {
}
