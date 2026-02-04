package com.paritoshpal.userservice.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException forId(Long userId) {
        return new UserNotFoundException("User with id " + userId + " not found");
    }


    public static UserNotFoundException forEmail(String email) {
        return new UserNotFoundException("User with email " + email + " not found");
    }


}
