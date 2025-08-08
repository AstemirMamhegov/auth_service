package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderItemDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.orderservice.dto.CreateOrderRequest;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public Order create(@RequestBody CreateOrderRequest request) {
        return service.createOrder(request);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getByUser(@PathVariable String userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
