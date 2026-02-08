package com.paritoshpal.restaurantservice.domain.exceptions;

public class InvalidOwnerException extends RuntimeException {
    public InvalidOwnerException(String message) {
        super(message);
    }

    public static InvalidOwnerException notFound(Long ownerId) {
        return new InvalidOwnerException("User with ID " + ownerId + " was not found in User Service.");
    }

    public static InvalidOwnerException unAuthorizedRole(Long ownerId, String role) {
        return new InvalidOwnerException("User with ID " + ownerId + " has role " + role + " and is not authorized to own a restaurant.");    }
}
