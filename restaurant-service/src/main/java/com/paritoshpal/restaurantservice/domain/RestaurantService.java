package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.*;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(CreateRestaurantRequest request);
    RestaurantResponse updateRestaurant(Long restaurantId, RestaurantUpdateRequest request);
    RestaurantResponse updateRestaurantStatus(Long restaurantId, Status status);
    void deleteRestaurant(Long restaurantId);


    RestaurantDetailedResponse getRestaurantById(Long restaurantId);


    PageResult<RestaurantResponse> searchRestaurants(Long ownerId, String name, String cuisine,String city,String query,int pageNo);


}
