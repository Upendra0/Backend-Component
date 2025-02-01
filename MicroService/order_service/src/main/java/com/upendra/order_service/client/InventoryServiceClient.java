package com.upendra.order_service.client;

import com.upendra.order_service.dto.BookStockRequest;
import com.upendra.order_service.dto.BookStockResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryServiceClient {

    @PostExchange("/api/inventory/v1/bookStock")
    BookStockResponse bookStock(@RequestBody BookStockRequest bookStockRequest);
}
