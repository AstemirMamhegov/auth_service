package com.example.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

@Service
class NotificationConsumer {

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("📨 Notification received: " + message);
    }
}
