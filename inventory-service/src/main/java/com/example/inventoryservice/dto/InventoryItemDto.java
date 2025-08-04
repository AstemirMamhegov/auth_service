package com.example.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryItemDto {
    private String productId;
    private int quantity;
}
