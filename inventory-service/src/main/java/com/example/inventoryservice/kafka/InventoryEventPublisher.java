package com.example.inventoryservice.kafka;

import com.example.inventoryservice.dto.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventPublisher {

    private final KafkaTemplate<String, Object> kafka;

    public void sendInventoryResult(InventoryReservedEvent event) {
        kafka.send("inventory.reserved", event.getOrderId(), event);
    }
}
