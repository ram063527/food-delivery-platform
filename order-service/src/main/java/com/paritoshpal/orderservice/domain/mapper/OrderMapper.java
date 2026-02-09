package com.paritoshpal.orderservice.domain.mapper;

import com.paritoshpal.orderservice.domain.models.OrderCreateRequest;
import com.paritoshpal.orderservice.domain.models.OrderDetailedResponse;
import com.paritoshpal.orderservice.domain.models.OrderResponse;
import com.paritoshpal.orderservice.domain.order.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class},
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {


    OrderResponse toOrderResponse(OrderEntity order);

    OrderDetailedResponse toOrderDetailedResponse(OrderEntity order);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true) // Generated in the service
    @Mapping(target = "customerId", ignore = true) // Set in the service based on authenticated user
    @Mapping(target = "orderStatus", ignore = true) // Default to CREATED in the entity
    @Mapping(target = "totalAmount", ignore = true) // Calculated in the service
    @Mapping(target = "deliveryFee", ignore = true) // Calculated in the service
    @Mapping(target = "discount", ignore = true) // Calculated in the service
    @Mapping(target = "tax", ignore = true) // Calculated in the service
    @Mapping(target = "createdAt", ignore = true) // Set by JPA
    @Mapping(target = "updatedAt", ignore = true) // Set by JPA
    @Mapping(target = "confirmedAt", ignore = true) // Set in the service when order is confirmed
    @Mapping(target = "deliveredAt", ignore = true) // Set in the service when order is delivered
    @Mapping(target = "cancelledAt", ignore = true) // Set in the service when order is cancelled
    OrderEntity toOrderEntity(OrderCreateRequest request);

}
