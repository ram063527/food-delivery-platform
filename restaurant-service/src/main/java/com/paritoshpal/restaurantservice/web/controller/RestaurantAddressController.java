package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.domain.RestaurantAddressService;
import com.paritoshpal.restaurantservice.domain.models.CreateRestaurantAddressRequest;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressResponse;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants-addresses")
@RequiredArgsConstructor
public class RestaurantAddressController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantAddressController.class);
    private final RestaurantAddressService restaurantAddressService;
    @PostMapping
    public ResponseEntity<RestaurantAddressResponse> createRestaurantAddress(
            @RequestBody @Valid CreateRestaurantAddressRequest request
    ) {

        log.info("Received request to create address for restaurantId: {}", request.restaurantId());
        RestaurantAddressResponse createdAddress = restaurantAddressService.createRestaurantAddress(request);
        log.info("Created restaurant address with id: {}", createdAddress.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<RestaurantAddressResponse> updateRestaurantAddress(
            @PathVariable Long addressId,
            @RequestBody @Valid RestaurantAddressUpdateRequest request
    ) {
        log.info("Received request to update restaurant address with id: {}", addressId);
        RestaurantAddressResponse updatedAddress =
                restaurantAddressService.updateRestaurantAddress(addressId, request);
        log.info("Updated restaurant address with id: {}", updatedAddress.id());
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteRestaurantAddress(
            @PathVariable Long addressId
    ) {
        log.info("Received request to delete restaurant address with id: {}", addressId);
        restaurantAddressService.deleteRestaurantAddress(addressId);
        log.info("Deleted restaurant address with id: {}", addressId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurantAddressByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        log.info("Received request to delete address for restaurant with id: {}", restaurantId);
        restaurantAddressService.deleteRestaurantAddressByRestaurantId(restaurantId);
        log.info("Deleted address for restaurant with id: {}", restaurantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantAddressResponse> getAddressByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        log.info("Received request to get address for restaurant with id: {}", restaurantId);
        RestaurantAddressResponse address =
                restaurantAddressService.getAddressByRestaurantId(restaurantId);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<RestaurantAddressResponse> getRestaurantAddressById(
            @PathVariable Long addressId
    ) {
        log.info("Received request to get restaurant address with id: {}", addressId);
        RestaurantAddressResponse address =
                restaurantAddressService.getRestaurantAddressById(addressId);
        return ResponseEntity.ok(address);
    }

}
