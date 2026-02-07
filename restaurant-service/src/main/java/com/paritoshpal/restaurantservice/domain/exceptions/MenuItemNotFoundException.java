package com.paritoshpal.restaurantservice.domain.exceptions;

public class MenuItemNotFoundException extends RuntimeException{
    public MenuItemNotFoundException(String s) {
        super(s);
    }

    public static MenuItemNotFoundException forId(Long id) {
        return new MenuItemNotFoundException("Menu item with id " + id + " not found.");
    }

}
