package com.paritoshpal.userservice.domain.models;

public record AddressUpdateRequest(
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country
) {
}
