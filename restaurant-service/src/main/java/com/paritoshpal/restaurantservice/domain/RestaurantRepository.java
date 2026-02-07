package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long>, JpaSpecificationExecutor<RestaurantEntity> {

    List<RestaurantEntity> findByOwnerId(Long ownerId);

    List<RestaurantEntity> findByNameIgnoreCase(String name);

    List<RestaurantEntity> findByCuisineIgnoreCase(String cuisine);


    List<RestaurantEntity> findByAddress_CityIgnoreCase(String city);
}
