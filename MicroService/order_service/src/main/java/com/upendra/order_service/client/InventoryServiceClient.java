package com.upendra.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryServiceClient {

    @GetMapping("/api/inventory/v1/getAll?page=0&size=5")
    String getAllStock();
}
