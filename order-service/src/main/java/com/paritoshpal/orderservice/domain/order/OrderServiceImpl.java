package com.paritoshpal.orderservice.domain.order;

import com.paritoshpal.orderservice.domain.mapper.OrderMapper;
import com.paritoshpal.orderservice.domain.models.OrderCreateRequest;
import com.paritoshpal.orderservice.domain.models.OrderDetailedResponse;
import com.paritoshpal.orderservice.domain.models.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;



    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {

        return null;
    }

    @Override
    public OrderDetailedResponse getOrderByOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    public OrderDetailedResponse getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderDetailedResponse> getOrderByCustomerId(Long customerId) {
        return List.of();
    }

    @Override
    public void cancelOrder(String orderNumber) {

    }
}
