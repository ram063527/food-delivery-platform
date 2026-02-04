package com.paritoshpal.userservice.domain.models;

public record AddressResponse(
        Long id,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country
) {
}
