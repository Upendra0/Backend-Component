package com.upendra.inventory_service.controller;

import com.upendra.inventory_service.dto.BookStockRequest;
import com.upendra.inventory_service.dto.BookStockResponse;
import com.upendra.inventory_service.dto.InventoryRequest;
import com.upendra.inventory_service.dto.InventoryResponse;
import com.upendra.inventory_service.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/v1/getAll")
    public ResponseEntity<List<InventoryResponse>> getAll(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(inventoryService.getAllInventory(page, size));
    }

    @PostMapping("/v1/addStock")
    public ResponseEntity<InventoryResponse> addStock(@RequestBody InventoryRequest inventoryRequest) {
        return ResponseEntity.ok(inventoryService.addStock(inventoryRequest));
    }

    @PostMapping("/v1/bookStock")
    public ResponseEntity<BookStockResponse> bookStock(@RequestBody BookStockRequest bookStockRequest) {
        return ResponseEntity.ok(inventoryService.bookStock(bookStockRequest));
    }
}
