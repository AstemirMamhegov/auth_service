package com.example.notificationservice.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "notifications")
public class NotificationLog {

    @Id
    private UUID id;

    private UUID userId;

    private String type;

    private String recipient;

    private String message;

    private Long sentAt;

    private String status;
}
