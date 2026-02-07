package com.paritoshpal.restaurantservice.domain.exceptions;

public class RestaurantAddressAlreadyExistsException extends RuntimeException {
    public RestaurantAddressAlreadyExistsException(String message) {
        super(message);
    }

    public static RestaurantAddressAlreadyExistsException forRestaurantId(Long restaurantId) {
        return new RestaurantAddressAlreadyExistsException("Restaurant with id " + restaurantId + " already has an address.");
    }
}
