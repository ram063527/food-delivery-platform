package com.paritoshpal.restaurantservice.domain.exceptions;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public static RestaurantNotFoundException forId(Long restaurantId) {
        return new RestaurantNotFoundException("Restaurant with ID " + restaurantId + " not found.");
    }
}
