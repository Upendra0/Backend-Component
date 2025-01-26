package com.upendra.order_service.service;

import com.upendra.order_service.dto.OrderRequest;
import com.upendra.order_service.dto.OrderResponse;
import com.upendra.order_service.model.Order;
import com.upendra.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        order.setPrice(BigDecimal.valueOf(orderRequest.price()));
        orderRepository.save(order);
        log.info("Created order, id: {}", order.getOrderId());
        return new OrderResponse(order.getOrderId(), order.getSkuCode(), order.getQuantity(), order.getPrice().doubleValue());
    }
}
