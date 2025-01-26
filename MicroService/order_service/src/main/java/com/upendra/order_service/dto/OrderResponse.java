package com.upendra.order_service.dto;

public record OrderResponse(String orderId, String skuCode, Integer quantity, Double price) {
}
