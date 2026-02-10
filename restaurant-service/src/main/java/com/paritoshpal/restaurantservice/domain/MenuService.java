package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.models.CreateMenuRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuSummaryResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuUpdateRequest;

import java.math.BigDecimal;
import java.util.List;

public interface MenuService {

    MenuResponse createMenu(Long restaurantId, CreateMenuRequest createMenuRequest);
    List<MenuSummaryResponse> getMenusByRestaurantId(Long restaurantId);
    void deleteMenuById(Long menuId);
    MenuResponse getMenuById(Long restaurantId, Long menuId);
    MenuResponse updateMenu(Long restaurantId, Long menuId, MenuUpdateRequest updateMenuRequest);
    void updateAllPricesInMenu(Long restaurantId, Long menuId, BigDecimal percentageIncrease);

}
