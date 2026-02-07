package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByMenuId(Long menuId);

    List<MenuItemEntity> findByMenu_IdAndCategory(Long menuId, MenuCategory category);
}
