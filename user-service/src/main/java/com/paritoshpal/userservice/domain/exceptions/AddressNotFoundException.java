package com.paritoshpal.userservice.domain.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }
    public static AddressNotFoundException forId(Long id){
        return new AddressNotFoundException("Address with id " + id + " not found");
    }
    public static AddressNotFoundException forUserId(Long userId){
        return new AddressNotFoundException("Address for user with id " + userId + " not found");
    }
    public  static AddressNotFoundException forUserEmail(String email){
        return new AddressNotFoundException("Address for user with email " + email + " not found");
    }
}
