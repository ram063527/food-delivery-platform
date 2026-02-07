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

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantUpdateRequest request
    ) {
        log.info("Received request to update restaurant with ID: {}", restaurantId);
        RestaurantResponse updatedRestaurant = restaurantService.updateRestaurant(restaurantId, request);
        log.info("Updated restaurant with ID: {}", restaurantId);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @PatchMapping("/{restaurantId}/status")
    public ResponseEntity<RestaurantResponse> updateRestaurantStatus(
            @PathVariable Long restaurantId,
            @RequestParam Status status
    ) {
        log.info("Received request to update status of restaurant with ID: {} to {}", restaurantId, status);
        RestaurantResponse updated = restaurantService.updateRestaurantStatus(restaurantId, status);
        log.info("Updated restaurant status for ID: {} to {}", restaurantId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        log.info("Received request to delete restaurant with ID: {}", restaurantId);
        restaurantService.deleteRestaurant(restaurantId);
        log.info("Deleted restaurant with ID: {}", restaurantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{restaurantId}")
    public ResponseEntity<RestaurantDetailedResponse> getRestaurantById(@PathVariable Long restaurantId) {
        log.info("Received request to get restaurant details for ID: {}", restaurantId);
        RestaurantDetailedResponse restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping
    public ResponseEntity<PageResult<RestaurantResponse>> getAllRestaurants(
            @RequestParam(defaultValue = "1") int pageNo
    ) {
        log.info("Received request to get all restaurants (page: {})", pageNo);
        PageResult<RestaurantResponse> restaurants = restaurantService.getAllRestaurants(pageNo);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByOwnerId(@PathVariable Long ownerId) {
        log.info("Received request to get restaurants for owner with ID: {}", ownerId);
        List<RestaurantResponse> restaurants = restaurantService.getRestaurantsByOwnerId(ownerId);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByName(@PathVariable String name) {
        log.info("Received request to get restaurants with name: {}", name);
        List<RestaurantResponse> restaurants = restaurantService.getRestaurantsByName(name);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByCuisine(@PathVariable String cuisine) {
        log.info("Received request to get restaurants with cuisine: {}", cuisine);
        List<RestaurantResponse> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByCity(@PathVariable String city) {
        log.info("Received request to get restaurants in city: {}", city);
        List<RestaurantResponse> restaurants = restaurantService.getRestaurantsByCity(city);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResult<RestaurantResponse>> searchRestaurants(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "1") int pageNo
    ) {
        log.info("Received search request for restaurants with filters - query: {}, name: {}, cuisine: {}, city: {}",
                query, name, cuisine, city);
        PageResult<RestaurantResponse> result = restaurantService.searchRestaurants(query, name, cuisine, city, pageNo);
        return ResponseEntity.ok(result);
    }

}
