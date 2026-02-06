package com.paritoshpal.restaurantservice.domain.mapper;

import com.paritoshpal.restaurantservice.domain.MenuEntity;
import com.paritoshpal.restaurantservice.domain.models.CreateMenuRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class},
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface MenuMapper {


    MenuResponse toMenuResponse(MenuEntity menu);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true) // Handle in service layer
    @Mapping(target = "menuItems", ignore = true) // Handle in service layer
    @Mapping(target = "createdAt", ignore = true) // Automatic
    @Mapping(target = "updatedAt", ignore = true) // Automatic
    MenuEntity toMenuEntity(CreateMenuRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true) // Handle in service layer
    @Mapping(target = "menuItems", ignore = true) // Handle in service layer
    @Mapping(target = "createdAt", ignore = true) // Do not update
    @Mapping(target = "updatedAt", ignore = true) // Automatic

    void updateMenuEntityFromRequest(MenuUpdateRequest request, @MappingTarget MenuEntity menu);
}
