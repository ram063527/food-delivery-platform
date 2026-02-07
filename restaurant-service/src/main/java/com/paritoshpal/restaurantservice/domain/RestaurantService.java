package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.*;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(CreateRestaurantRequest request);
    RestaurantResponse updateRestaurant(Long restaurantId, RestaurantUpdateRequest request);
    RestaurantResponse updateRestaurantStatus(Long restaurantId, Status status);
    void deleteRestaurant(Long restaurantId);


    RestaurantDetailedResponse getRestaurantById(Long restaurantId);


    PageResult<RestaurantResponse> getAllRestaurants(int pageNo);
    List<RestaurantResponse> getRestaurantsByOwnerId(Long ownerId);


    List<RestaurantResponse> getRestaurantsByName(String name);
    List<RestaurantResponse> getRestaurantsByCuisine(String cuisine);
    List<RestaurantResponse> getRestaurantsByCity(String city);


    PageResult<RestaurantResponse> searchRestaurants(String query, String name, String cuisine, String city,  int pageNo);


}
