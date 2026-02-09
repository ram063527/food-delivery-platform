package com.paritoshpal.orderservice.domain.order;

import com.paritoshpal.orderservice.clients.user.AddressResponse;
import com.paritoshpal.orderservice.domain.OrderStatus;
import com.paritoshpal.orderservice.domain.models.OrderCreateRequest;
import com.paritoshpal.orderservice.domain.models.OrderDetailedResponse;
import com.paritoshpal.orderservice.domain.models.OrderResponse;
import com.rabbitmq.client.Address;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest request);
    OrderDetailedResponse getOrderByOrderNumber(String orderNumber);
    OrderDetailedResponse getOrderById(Long orderId);
    List<OrderDetailedResponse> getOrderByCustomerId(Long customerId); // Only allow the logged in customer to fetch their orders and the admins
    void cancelOrder(String orderNumber);

    // Customer Facing

    List<OrderResponse> getOrdersForRestaurant(Long restaurantId); // Only allow the restaurant owner and the admins to fetch orders for a restaurant
    void confirmOrder(String orderNumber); // Only allow the restaurant owner to confirm an order
    void rejectOrder(String orderNumber); // Only allow the restaurant owner to reject an order
    void markAsPreparing(String orderNumber); // Only allow the restaurant owner to mark an order as preparing
    void markAsReadyForPickup(String orderNumber); // Only allow the restaurant owner to mark

    // Delivery Personnel Facing
    AddressResponse  getDeliveryAddressForOrder(String orderNumber);
    void pickUpOrder(String orderNumber);
    void markDelivered(String orderNumber);

    // Admin Facing
    List<OrderResponse> getAllOrders(); // Only allow admins to fetch all orders
    List<OrderResponse> getOrderByStatus(OrderStatus status); // Only allow admins to fetch orders by status


}
