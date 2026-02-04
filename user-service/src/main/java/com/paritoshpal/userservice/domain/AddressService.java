package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.models.AddressResponse;
import com.paritoshpal.userservice.domain.models.AddressUpdateRequest;
import com.paritoshpal.userservice.domain.models.CreateAddressRequest;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(CreateAddressRequest request);
    AddressResponse getAddressById(Long addressId);
    AddressResponse updateAddress(Long addressId, AddressUpdateRequest request);
    void deleteAddress(Long addressId);
    List<AddressResponse> getAddressesByUserEmail(String email);
    List<AddressResponse> getAddressesByUserId(Long userId);
}
