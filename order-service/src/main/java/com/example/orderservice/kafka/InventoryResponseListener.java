package com.example.orderservice.kafka;

import com.example.orderservice.dto.InventoryReservedEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.example.orderservice.model.OrderStatus;

@Component
@RequiredArgsConstructor
public class InventoryResponseListener {

    private final OrderRepository orderRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "inventory.reserved", groupId = "order-service-group")
    public void listen(String message) {
        try {
            InventoryReservedEvent event = mapper.readValue(message, InventoryReservedEvent.class);
            Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
            order.setStatus(event.isSuccess() ? OrderStatus.RESERVED : OrderStatus.CANCELLED);
            orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
