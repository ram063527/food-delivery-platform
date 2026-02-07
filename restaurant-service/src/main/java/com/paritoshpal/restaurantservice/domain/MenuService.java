package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.CreateMenuRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuSummaryResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuUpdateRequest;

import java.util.List;

public interface MenuService {

    MenuResponse createMenu(CreateMenuRequest createMenuRequest);
    List<MenuSummaryResponse> getMenusByRestaurantId(Long restaurantId);
    void deleteMenuById(Long menuId);
    MenuResponse getMenuById(Long menuId);
    MenuResponse updateMenu(Long menuId, MenuUpdateRequest updateMenuRequest);


}
