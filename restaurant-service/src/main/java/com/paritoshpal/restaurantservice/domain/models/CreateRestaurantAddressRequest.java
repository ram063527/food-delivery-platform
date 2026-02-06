package com.paritoshpal.restaurantservice.domain.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRestaurantAddressRequest(
        @NotNull(message = "Restaurant ID cannot be null")
        Long restaurantId,
        @NotBlank(message = "Street address cannot be empty")
        String streetAddress,
        @NotBlank(message = "City cannot be empty")
        String city,
        @NotBlank(message = "State cannot be empty")
        String state,
        @NotBlank(message = "Postal code cannot be empty")
        String postalCode,
        @NotBlank(message = "Country cannot be empty")
        String country,
        Double latitude,
        Double longitude
) {
}
