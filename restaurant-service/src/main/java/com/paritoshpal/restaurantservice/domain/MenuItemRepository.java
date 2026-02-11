package com.paritoshpal.restaurantservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByMenuId(Long menuId);

    List<MenuItemEntity> findByMenu_IdAndCategory(Long menuId, MenuCategory category);

    Optional<MenuItemEntity> findByIdAndMenu_Id(Long id, Long menuId);

    List<MenuItemEntity> findAllByMenu_IdAndIdIn(Long menuId, List<Long> ids);

}
