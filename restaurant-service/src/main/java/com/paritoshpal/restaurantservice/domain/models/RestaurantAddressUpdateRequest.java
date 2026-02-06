package com.paritoshpal.restaurantservice.domain.models;

public record RestaurantAddressUpdateRequest(
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        Double latitude,
        Double longitude
) {
}
