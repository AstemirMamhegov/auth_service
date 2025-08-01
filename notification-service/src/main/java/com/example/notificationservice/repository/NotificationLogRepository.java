package com.example.notificationservice.repository;

import com.example.notificationservice.document.NotificationLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;
import java.util.List;

public interface NotificationLogRepository extends MongoRepository<NotificationLog, UUID> {
    List<NotificationLog> findByUserId(UUID userId);
}
