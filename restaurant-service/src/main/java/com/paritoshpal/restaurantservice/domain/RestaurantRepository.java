package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<MenuEntity,Long> {

}
