package com.upendra.order_service.client;

import com.upendra.order_service.dto.BookStockRequest;
import com.upendra.order_service.dto.BookStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @PostMapping("/api/inventory/v1/bookStock")
    BookStockResponse bookStock(BookStockRequest bookStockRequest);
}
