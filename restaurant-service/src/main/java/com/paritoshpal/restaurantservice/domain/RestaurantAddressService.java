package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.CreateRestaurantAddressRequest;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressResponse;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressUpdateRequest;

public interface RestaurantAddressService {

    RestaurantAddressResponse createRestaurantAddress(CreateRestaurantAddressRequest request);
    RestaurantAddressResponse updateRestaurantAddress(Long restaurantAddressId, RestaurantAddressUpdateRequest request);
    void deleteRestaurantAddress(Long restaurantAddressId);
    void deleteRestaurantAddressByRestaurantId(Long restaurantId);
    RestaurantAddressResponse getAddressByRestaurantId(Long restaurantId);
    RestaurantAddressResponse getRestaurantAddressById(Long restaurantAddressId);


}
