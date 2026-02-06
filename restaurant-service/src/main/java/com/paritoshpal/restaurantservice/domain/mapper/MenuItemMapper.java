package com.paritoshpal.restaurantservice.domain.mapper;

import com.paritoshpal.restaurantservice.domain.MenuItemEntity;
import com.paritoshpal.restaurantservice.domain.models.CreateMenuItemRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuItemResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuItemUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;




@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MenuItemMapper {

    MenuItemResponse toMenuItemResponse(MenuItemEntity menuItem);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menu", ignore = true) // Handle in service layer
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MenuItemEntity toMenuItemEntity(CreateMenuItemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menu", ignore = true) // Handle in service layer
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateMenuItemFromRequest(MenuItemUpdateRequest request, @MappingTarget MenuItemEntity menuItem);

}
