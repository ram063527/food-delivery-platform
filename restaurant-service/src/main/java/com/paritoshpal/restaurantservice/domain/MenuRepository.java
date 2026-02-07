package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<MenuEntity,Long>
{
    List<MenuEntity> findByRestaurantId(Long restaurantId);
}
