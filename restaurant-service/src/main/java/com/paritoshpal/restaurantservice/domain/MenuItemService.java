package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.CreateMenuItemRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuItemResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuItemUpdateRequest;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(
            Long restaurantId,
            Long menuId,
            CreateMenuItemRequest request);

    MenuItemResponse getMenuItemById(
            Long restaurantId,
            Long menuId,
            Long menuItemId
    );

    MenuItemResponse updateMenuItem(
            Long restaurantId,
            Long menuId,
            Long id,
            MenuItemUpdateRequest request
    );

    void deleteMenuItem(
            Long restaurantId,
            Long menuId,
            Long id
    );

    void updateAvailability(
            Long restaurantId,
            Long menuId,
            Long id,
            boolean available);


    List<MenuItemResponse> getMenuItemsByMenuId(
            Long restaurantId,
            Long id
    );

    List<MenuItemResponse> getMenuItemsByCategory(
            Long restaurantId,
            Long menuId,
            MenuCategory category);


    List<MenuItemResponse> getMenuItemsBulk(
            Long restaurantId,
            Long menuId,
            List<Long> ids
    );

}
