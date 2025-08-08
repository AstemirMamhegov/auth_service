package com.example.inventoryservice.service;

import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    public boolean isInStock(String productId, int quantity) {
        return repository.findById(productId)
                .map(inv -> inv.getQuantity() >= quantity)
                .orElse(false);
    }
}
