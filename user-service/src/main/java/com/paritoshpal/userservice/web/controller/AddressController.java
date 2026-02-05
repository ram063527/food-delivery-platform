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
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private static final Logger log = LoggerFactory.getLogger(AddressController.class);
    private final AddressService addressService;


    @GetMapping("/id/{id}")
    public ResponseEntity<AddressResponse> getAddressById(
            @PathVariable  Long id
    ) {
        log.info("Received request to get address with id: {}", id);
        AddressResponse address = addressService.getAddressById(id);
        log.info("Returning address with id: {}", address);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<AddressResponse>> getAddressByUserEmail(@PathVariable String email) {
        log.info("Received request to get address for user with email: {}", email);
        List<AddressResponse> addressResponse = addressService.getAddressesByUserEmail(email
        );
        log.info("Returning address for user with email: {}", email);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<AddressResponse> getAddressByUserId(@PathVariable  Long userId) {
        log.info("Received request to get address for user with id: {}", userId);
        AddressResponse address = addressService.getAddressesByUserId(userId).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No address found for user with id: " + userId));
        log.info("Returning address for user with id: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }


    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @RequestBody @Valid CreateAddressRequest request
    ){
        log.info("Received request to create address for user");
        // for now passing userid in the request itself
        AddressResponse createdAddress = addressService.createAddress(request);
        log.info("Created address with id: {} for user id: {}", createdAddress.id(), request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long id,
            @RequestBody @Valid AddressUpdateRequest request
    ){
        log.info("Received request to update address with id: {}", id);
        AddressResponse updatedAddress = addressService.updateAddress(id, request);
        log.info("Updated address with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long id
    ) {
        log.info("Received request to delete address with id: {}", id);
        addressService.deleteAddress(id);
        log.info("Deleted address with id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
