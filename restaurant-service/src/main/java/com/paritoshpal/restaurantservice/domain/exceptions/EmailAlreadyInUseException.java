package com.paritoshpal.restaurantservice.domain.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String s) {
    }

    public static EmailAlreadyInUseException forEmail(String email) {
        return new EmailAlreadyInUseException("Email " + email + " is already in use.");
    }
}
