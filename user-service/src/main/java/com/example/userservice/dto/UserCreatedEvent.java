package com.example.userservice.dto;

import com.example.userservice.model.Role;
import lombok.Data;

@Data
public class UserCreatedEvent {
    private String userId;
    private String email;
    private String fullName;
    private Role role;
}
