package com.paritoshpal.orderservice.domain.mapper;

import com.paritoshpal.orderservice.domain.models.OrderItemCreateRequest;
import com.paritoshpal.orderservice.domain.models.OrderItemResponse;
import com.paritoshpal.orderservice.domain.orderItem.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper {


    @Mapping(target = "menuItemName", ignore = true) //
    OrderItemResponse toOrderItemResponse(OrderItemEntity orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true) // Set in the service when associating with OrderEntity
    @Mapping(target = "unitPrice", ignore = true) // Set in the service based on menu item price
    @Mapping(target = "subTotal", ignore = true) // Calculated in the service
    OrderItemEntity toOrderItemEntity(OrderItemCreateRequest request);


}
