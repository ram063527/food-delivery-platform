package com.paritoshpal.orderservice.domain.order;

import com.paritoshpal.orderservice.domain.models.OrderCreateRequest;
import com.paritoshpal.orderservice.domain.models.OrderDetailedResponse;
import com.paritoshpal.orderservice.domain.models.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest request);
    OrderDetailedResponse getOrderByOrderNumber(String orderNumber);
    OrderDetailedResponse getOrderById(Long orderId);
    List<OrderDetailedResponse> getOrderByCustomerId(Long customerId); // Only allow the logged in customer to fetch their orders and the admins
    void cancelOrder(String orderNumber);

}
