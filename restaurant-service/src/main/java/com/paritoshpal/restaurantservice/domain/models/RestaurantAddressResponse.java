package com.paritoshpal.restaurantservice.domain.models;

public record RestaurantAddressResponse(
        Long id,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        Double latitude,
        Double longitude
) {
}
