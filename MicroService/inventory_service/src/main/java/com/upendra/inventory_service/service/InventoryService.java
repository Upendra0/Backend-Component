package com.upendra.inventory_service.service;

import com.upendra.inventory_service.dto.BookStockRequest;
import com.upendra.inventory_service.dto.BookStockResponse;
import com.upendra.inventory_service.dto.InventoryRequest;
import com.upendra.inventory_service.dto.InventoryResponse;
import com.upendra.inventory_service.model.Inventory;
import com.upendra.inventory_service.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryResponse> getAllInventory(int page, int size){
        return inventoryRepository.findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map((inventory -> new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity())))
                .toList();
    }

    public InventoryResponse addStock(InventoryRequest inventoryRequest){
        logger.info("Adding inventory for skuCode: {}, quantity: {}", inventoryRequest.skuCode(), inventoryRequest.quantity());
        Inventory inventory = inventoryRepository.findBySkuCode(inventoryRequest.skuCode())
                .orElse(new Inventory(inventoryRequest.skuCode(),0));
        inventory.setQuantity(inventory.getQuantity() + inventoryRequest.quantity());
        inventoryRepository.save(inventory);
        return new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity());
    }

    public BookStockResponse bookStock(BookStockRequest bookStockRequest) {
        logger.info("Booking stock for skuCode: {}, quantity: {}", bookStockRequest.skuCode(), bookStockRequest.quantity());
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(bookStockRequest.skuCode());
        if(inventoryOptional.isEmpty()){
            return new BookStockResponse(false, "Inventory not found for skuCode: " + bookStockRequest.skuCode());
        } else{
            Inventory inventory = inventoryOptional.get();
            if(inventory.getQuantity()<bookStockRequest.quantity()){
                return new BookStockResponse(false, String.format("Insufficient stock for skuCode: %s, requested: %d, available: %d.", bookStockRequest.skuCode(), bookStockRequest.quantity(), inventory.getQuantity()));
            } else{
                inventory.setQuantity(inventory.getQuantity() - bookStockRequest.quantity());
                inventoryRepository.save(inventory);
                logger.info("Stock booked successfully for skuCode: {}", bookStockRequest.skuCode());
                return new BookStockResponse(true, "Stock booked successfully for skuCode: " + bookStockRequest.skuCode());
            }
        }
    }
}
