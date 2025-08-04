package com.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    private String email;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role;
}
