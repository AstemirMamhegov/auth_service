package com.example.orderservice.kafka;

import com.example.orderservice.kafka.avro.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderStatusChangedEvent> kafkaTemplate;

    public void publishStatusChanged(OrderStatusChangedEvent event) {
        kafkaTemplate.send(new ProducerRecord<>("order.status-changed", event.getOrderId(), event));
    }
}
