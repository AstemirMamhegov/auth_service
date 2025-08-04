package com.example.inventoryservice.kafka;

import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryRequestListener {

    private final InventoryRepository repository;
    private final InventoryEventPublisher publisher;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
            e.printStackTrace();
        }
    }
}
