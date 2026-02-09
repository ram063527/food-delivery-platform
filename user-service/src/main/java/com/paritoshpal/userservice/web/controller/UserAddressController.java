package com.paritoshpal.userservice.web.controller;

import com.paritoshpal.userservice.domain.AddressService;
import com.paritoshpal.userservice.domain.models.AddressResponse;
import com.paritoshpal.userservice.domain.models.AddressUpdateRequest;
import com.paritoshpal.userservice.domain.models.CreateAddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
@RequiredArgsConstructor
public class UserAddressController {

    private static final Logger log = LoggerFactory.getLogger(UserAddressController.class);
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddressByUserId(@PathVariable  Long userId) {
        log.info("Received request to get address for user with id: {}", userId);
        List<AddressResponse> address = addressService.getAddressesByUserId(userId);
        log.info("Returning address for user with id: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(
            @PathVariable  Long userId,
            @PathVariable  Long addressId
    ) {
        log.info("Received request to get address with id: {} for user with id: {}", addressId, userId);
        AddressResponse address = addressService.getAddressById(addressId);
        log.info("Returning address with id: {} for user with id: {}", addressId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }


    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @PathVariable  Long userId,
            @RequestBody @Valid CreateAddressRequest request
    ){
        log.info("Received request to create address for user");
        // for now passing userid in the request itself
        AddressResponse createdAddress = addressService.createAddress(userId,request);
        log.info("Created address with id: {} for user with id: {}", createdAddress.id(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable  Long userId,
            @PathVariable Long addressId,
            @RequestBody @Valid AddressUpdateRequest request
    ){
        log.info("Received request to update address with id: {} for user with id: {}", addressId, userId);
        AddressResponse updatedAddress = addressService.updateAddress(addressId, request);
        log.info("Updated address with id: {} for user with id: {}", addressId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable  Long userId,
            @PathVariable Long addressId
    ) {
        log.info("Received request to delete address with id: {} for user with id: {}", addressId, userId);
        addressService.deleteAddress(addressId);
        log.info("Deleted address with id: {} for user with id: {}", addressId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
