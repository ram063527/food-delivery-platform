package com.paritoshpal.orderservice.clients.user;

public record AddressResponse(
        Long id,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country
) {
}
