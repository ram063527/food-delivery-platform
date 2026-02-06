package com.paritoshpal.restaurantservice.domain.models;

import com.paritoshpal.restaurantservice.domain.Status;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public record RestaurantDetailedResponse(
        Long id,
        String name,
        String description,
        String phone,
        String email,
        String cuisine,
        BigDecimal rating,
        Status status,
        List<MenuResponse> menus,
        RestaurantAddressResponse address,
        LocalTime openingTime,
        LocalTime closingTime,
        BigDecimal deliveryFee,
        BigDecimal minimumOrderAmount,
        Integer estimatedDeliveryTimeInMinutes
) {
}
