package com.example.inventoryservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class InventoryRequestEvent {
    private String orderId;
    private List<InventoryItemDto> items;
}
