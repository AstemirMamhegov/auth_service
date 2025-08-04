package com.example.userservice.kafka;

import com.example.userservice.dto.UserCreatedEvent;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user.created", groupId = "user-service-group")
    public void handleUserCreated(String message) {
        try {
            UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
            User user = User.builder()
                    .id(event.getUserId())
                    .email(event.getEmail())
                    .fullName(event.getFullName())
                    .role(event.getRole())
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
