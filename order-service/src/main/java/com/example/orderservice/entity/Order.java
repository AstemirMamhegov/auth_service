package com.example.orderservice.entity;

import jakarta.persistence.*;
import java.util.UUID;
import com.example.orderservice.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Double totalAmount;

    private Long createdAt;

    private Long updatedAt;
}
