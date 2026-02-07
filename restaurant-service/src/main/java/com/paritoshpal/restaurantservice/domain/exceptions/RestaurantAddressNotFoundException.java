package com.paritoshpal.restaurantservice.domain.exceptions;

public class RestaurantAddressNotFoundException extends RuntimeException {
    public RestaurantAddressNotFoundException(String message) {
        super(message);
    }

    public static RestaurantAddressNotFoundException forRestaurantId(Long restaurantId) {
        return new RestaurantAddressNotFoundException("Restaurant address not found for restaurant ID: " + restaurantId);
    }

        public static RestaurantAddressNotFoundException forAddressId(Long addressId) {
            return new RestaurantAddressNotFoundException("Restaurant address not found for address ID: " + addressId);
        }
}
