package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddressEntity,Long> {


    Optional<RestaurantAddressEntity> findByRestaurantId(Long restaurantId);
}
