package com.paritoshpal.restaurantservice.domain.models;

import java.util.List;

public record MenuResponse(
        Long id,
        String name,
        String description,
        List<MenuItemResponse> menuItems
) {
}
