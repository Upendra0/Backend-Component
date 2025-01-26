package com.upendra.order_service.dto;

public record OrderRequest(String skuCode, Integer quantity, Double price) {
}
