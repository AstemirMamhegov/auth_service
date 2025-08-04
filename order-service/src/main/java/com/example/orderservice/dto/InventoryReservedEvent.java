package com.example.orderservice.dto;

import lombok.Data;

@Data
public class InventoryReservedEvent {
    private String orderId;
    private boolean success;
}
