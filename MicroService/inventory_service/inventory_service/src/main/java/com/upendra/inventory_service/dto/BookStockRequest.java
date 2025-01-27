package com.upendra.inventory_service.dto;

public record BookStockRequest(String skuCode, Integer quantity) {
}
