package com.paritoshpal.restaurantservice.domain.mapper;

import com.paritoshpal.restaurantservice.domain.RestaurantAddressEntity;
import com.paritoshpal.restaurantservice.domain.models.CreateRestaurantAddressRequest;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressResponse;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestaurantAddressMapper {

    RestaurantAddressResponse toAddressResponse(RestaurantAddressEntity restaurantAddress);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true) // Remember to handle in service layer
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RestaurantAddressEntity toRestaurantAddressEntity(CreateRestaurantAddressRequest request);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true) // Remember to handle in service layer
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateRestaurantAddressFromRequest(RestaurantAddressUpdateRequest request, @MappingTarget RestaurantAddressEntity restaurantAddress);


}
