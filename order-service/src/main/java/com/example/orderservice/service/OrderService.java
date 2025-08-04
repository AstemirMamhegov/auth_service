package com.example.orderservice.service;

import com.example.orderservice.dto.OrderCreatedEvent;
import com.example.orderservice.dto.OrderItemDto;
import com.example.orderservice.kafka.OrderEventPublisher;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher publisher;

    public Order createOrder(String userId, List<OrderItemDto> items) {
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .status(OrderStatus.NEW)
                .createdAt(Instant.now())
                .items(items.stream().map(i -> OrderItem.builder()
                        .productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .build()).toList())
                .build();

        orderRepository.save(order);

        publisher.publishOrderCreated(new OrderCreatedEvent(order.getId(), items));
        return order;
    }

    public Optional<Order> getById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
