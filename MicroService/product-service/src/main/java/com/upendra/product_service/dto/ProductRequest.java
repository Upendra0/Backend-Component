package com.upendra.product_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequest (@NotBlank(message = "Product name can not be empty") String name,
                              @NotBlank(message = "Product description can not be empty") String description,
                              @DecimalMin(value = "0.0", message = "Price should be positive value") BigDecimal price) {}
