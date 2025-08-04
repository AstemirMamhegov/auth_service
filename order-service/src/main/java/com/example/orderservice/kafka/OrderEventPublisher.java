package com.example.orderservice.kafka;

import com.example.orderservice.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafka;

    public void publishOrderCreated(OrderCreatedEvent event) {
        kafka.send("order.created", event.getOrderId(), event);
    }
}
