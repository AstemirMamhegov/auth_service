package com.example.inventoryservice.kafka;

import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryRequestListener {

    private final InventoryRepository repository;
    private final InventoryEventPublisher publisher;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order.created", groupId = "inventory-service-group")
    public void handle(String message) {
        try {
            InventoryRequestEvent event = objectMapper.readValue(message, InventoryRequestEvent.class);

            boolean allAvailable = event.getItems().stream().allMatch(item -> {
                Inventory inv = repository.findById(item.getProductId()).orElse(null);
                return inv != null && inv.getQuantity() >= item.getQuantity();
            });

            if (allAvailable) {
                for (InventoryItemDto item : event.getItems()) {
                    Inventory inv = repository.findById(item.getProductId()).orElseThrow();
                    inv.setQuantity(inv.getQuantity() - item.getQuantity());
                    repository.save(inv);
                }
            }

            InventoryReservedEvent result = new InventoryReservedEvent(event.getOrderId(), allAvailable);
            publisher.sendInventoryResult(result);

        } catch (Exception e) {
            log.error("Failed to process inventory request", e);
        }
    }
}
