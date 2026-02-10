package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.domain.RestaurantService;
import com.paritoshpal.restaurantservice.domain.Status;
import com.paritoshpal.restaurantservice.domain.models.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @RequestBody @Valid CreateRestaurantRequest request
    ) {

        log.info("Received request to create restaurant with name: {}", request.name());
        RestaurantResponse createdRestaurant = restaurantService.createRestaurant(request);
        log.info("Created restaurant with ID: {}", createdRestaurant.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @RequestBody @Valid RestaurantUpdateRequest request
    ) {
        log.info("Received request to update restaurant with ID: {}", id);
        RestaurantResponse updatedRestaurant = restaurantService.updateRestaurant(id, request);
        log.info("Updated restaurant with ID: {}", id);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RestaurantResponse> updateRestaurantStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) {
        log.info("Received request to update status of restaurant with ID: {} to {}", id, status);
        RestaurantResponse updated = restaurantService.updateRestaurantStatus(id, status);
        log.info("Updated restaurant status for ID: {} to {}", id, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.info("Received request to delete restaurant with ID: {}", id);
        restaurantService.deleteRestaurant(id);
        log.info("Deleted restaurant with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailedResponse> getRestaurantById(@PathVariable Long id) {
        log.info("Received request to get restaurant details for ID: {}", id);
        RestaurantDetailedResponse restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }


    @GetMapping
    public ResponseEntity<PageResult<RestaurantResponse>> getRestaurants(
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int pageNo
    ) {
        log.info("Fetching restaurants with filters - owner: {}, name: {}, cuisine: {}, city: {}",
                ownerId, name, cuisine, city);
        PageResult<RestaurantResponse> result = restaurantService.searchRestaurants(ownerId,name,cuisine,city,query,pageNo);
        return ResponseEntity.ok(result);
    }

}
