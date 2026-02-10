package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MenuRepository extends JpaRepository<MenuEntity,Long>
{
    List<MenuEntity> findByRestaurantId(Long restaurantId);

    Optional<MenuEntity> findByIdAndRestaurantId(Long id, Long restaurantId);

}
