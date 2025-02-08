package com.upendra.product_service.dto;

public record FieldError(String fieldName, Object rejectedValue, String message) {
}
