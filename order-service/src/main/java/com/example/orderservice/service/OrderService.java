package com.example.orderservice.service;

import com.example.orderservice.dto.OrderCreatedEvent;
import com.example.orderservice.dto.OrderItemDto;
import com.example.orderservice.grpc.StockCheckClient;
import com.example.orderservice.grpc.StockCheckItem;
import com.example.orderservice.grpc.StockCheckRequest;
import com.example.orderservice.kafka.OrderEventPublisher;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.orderservice.kafka.avro.OrderStatusChangedEvent;
import com.example.orderservice.dto.CreateOrderRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(Instant.now());

        orderRepository.save(order);

        OrderStatusChangedEvent event = OrderStatusChangedEvent.newBuilder()
                .setOrderId(order.getId())
                .setUserId(order.getUserId())
                .setStatus(order.getStatus().name())
                .setCreatedAt(order.getCreatedAt())
                .build();

        orderEventPublisher.publishStatusChanged(event);

        return order;
    }
}

