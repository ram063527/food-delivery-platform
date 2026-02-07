package com.paritoshpal.restaurantservice.domain.exceptions;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(String s) {
        super(s);
    }

    public static MenuNotFoundException forId(Long id) {
        return new MenuNotFoundException("Menu with id " + id + " not found.");
    }
}
