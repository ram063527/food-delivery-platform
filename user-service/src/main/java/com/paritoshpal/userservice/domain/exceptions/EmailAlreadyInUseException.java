package com.paritoshpal.userservice.domain.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }

    public static EmailAlreadyInUseException forEmail(String email){
        return new EmailAlreadyInUseException("Email " + email + " is already in use");
    }
}
