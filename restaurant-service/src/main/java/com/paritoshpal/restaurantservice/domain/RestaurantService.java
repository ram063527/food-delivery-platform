package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.*;

import java.awt.print.Pageable;
import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(CreateRestaurantRequest request);
    RestaurantResponse updateRestaurant(Long restaurantId, RestaurantUpdateRequest request);
    RestaurantResponse updateRestaurantStatus(Long restaurantId, Status status);
    void deleteRestaurant(Long restaurantId);


    RestaurantDetailedResponse getRestaurantById(Long restaurantId);


    PageResult<RestaurantResponse> getAllRestaurants(Pageable pageable);
    List<RestaurantResponse> getRestaurantsByOwnerId(Long ownerId);


    List<RestaurantResponse> getRestaurantsByName(String name);
    List<RestaurantResponse> getRestaurantsByCuisine(String cuisine);
    List<RestaurantResponse> getRestaurantsByAddress(String city, String state, String country);


}
