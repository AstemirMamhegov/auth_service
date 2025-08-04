package com.example.notificationservice.dto;

import lombok.Data;

@Data
public class OrderStatusChangedEvent {
    private String orderId;
    private String userId;
    private String status;
}
