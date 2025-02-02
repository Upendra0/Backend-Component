package com.upendra.order_service.client;

import com.upendra.order_service.dto.BookStockRequest;
import com.upendra.order_service.dto.BookStockResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryServiceClient {

    @CircuitBreaker(name = "inventory")
    @Retry(name = "inventory")
    @PostExchange("/api/inventory/v1/bookStock")
    BookStockResponse bookStock(@RequestBody BookStockRequest bookStockRequest);
}
