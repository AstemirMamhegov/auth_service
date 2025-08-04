package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryReservedEvent {
    private String orderId;
    private boolean success;
}
