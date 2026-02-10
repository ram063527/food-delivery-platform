package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.CreateRestaurantAddressRequest;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressResponse;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressUpdateRequest;

public interface RestaurantAddressService {

    RestaurantAddressResponse createRestaurantAddress(Long restaurantId, CreateRestaurantAddressRequest request);
    RestaurantAddressResponse updateRestaurantAddress(Long restaurantId, RestaurantAddressUpdateRequest request);
    void deleteRestaurantAddressByRestaurantId(Long restaurantId);
    RestaurantAddressResponse getAddressByRestaurantId(Long restaurantId);


}
