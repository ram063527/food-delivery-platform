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
@RequestMapping("/api/restaurant/{restaurantId}/address")
@RequiredArgsConstructor
public class RestaurantAddressController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantAddressController.class);
    private final RestaurantAddressService restaurantAddressService;


    @GetMapping
    public ResponseEntity<RestaurantAddressResponse> getAddressByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        log.info("Received request to get address for restaurant with id: {}", restaurantId);
        RestaurantAddressResponse address =
                restaurantAddressService.getAddressByRestaurantId(restaurantId);
        return ResponseEntity.ok(address);


    }


    @PostMapping
    public ResponseEntity<RestaurantAddressResponse> createRestaurantAddress(
            @PathVariable Long restaurantId,
            @RequestBody @Valid CreateRestaurantAddressRequest request
    ) {
        log.info("REST request to save RestaurantAddress : {}", request);
        RestaurantAddressResponse createdAddress = restaurantAddressService.createRestaurantAddress(restaurantId, request);
        log.info("Created restaurant address with id: {}", createdAddress.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping
    public ResponseEntity<RestaurantAddressResponse> updateRestaurantAddress(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantAddressUpdateRequest request
    ) {
        log.info("REST request to update Restaurant address with restaurant id:  {}", restaurantId);
        RestaurantAddressResponse updatedAddress =
                restaurantAddressService.updateRestaurantAddress(restaurantId, request);
        log.info("Updated restaurant address with id: {}", updatedAddress.id());
        return ResponseEntity.ok(updatedAddress);
    }



    @DeleteMapping()
    public ResponseEntity<Void> deleteRestaurantAddressByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        log.info("Received request to delete address for restaurant with id: {}", restaurantId);
        restaurantAddressService.deleteRestaurantAddressByRestaurantId(restaurantId);
        log.info("Deleted address for restaurant with id: {}", restaurantId);
        return ResponseEntity.noContent().build();
    }




}
