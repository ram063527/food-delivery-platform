package com.paritoshpal.restaurantservice.domain.exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String message) {
        super(message);

    }

    public static OwnerNotFoundException withId(Long ownerId) {
        return new OwnerNotFoundException("Owner with ID " + ownerId + " was not found.");
    }



}
