package com.example.notificationservice.kafka;

import com.example.notificationservice.dto.OrderStatusChangedEvent;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.NotificationType;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.RateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class OrderStatusListener {

    private final NotificationRepository repository;
    private final RateLimitService rateLimitService;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "order.status-changed", groupId = "notification-service-group")
    public void listen(String message) {
        try {
            OrderStatusChangedEvent event = mapper.readValue(message, OrderStatusChangedEvent.class);

            if (!rateLimitService.allow(event.getUserId())) {
                System.out.println("Rate limit exceeded for user: " + event.getUserId());
                return;
            }

            Notification notification = Notification.builder()
                    .userId(event.getUserId())
                    .type(NotificationType.EMAIL)
                    .content("Ваш заказ " + event.getOrderId() + " теперь в статусе: " + event.getStatus())
                    .createdAt(Instant.now())
                    .build();

            repository.save(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
