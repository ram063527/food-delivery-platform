package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.CreateMenuItemRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuItemResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuItemUpdateRequest;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(CreateMenuItemRequest request);

    MenuItemResponse getMenuItemById(Long menuItemId);

    MenuItemResponse updateMenuItem(Long menuItemId, MenuItemUpdateRequest request);

    void deleteMenuItem(Long menuItemId);

    List<MenuItemResponse> getMenuItemsByMenuId(Long menuId);

    List<MenuItemResponse> getMenuItemsByCategory(Long menuId, MenuCategory category);

    void updateAvailability(Long menuItemId, boolean available);

}
