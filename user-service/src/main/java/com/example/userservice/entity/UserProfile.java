package com.example.userservice.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    private String phone;

    private String avatarUrl;

    private String country;

    private String city;

    private String timezone;

    @Column(nullable = false, updatable = false)
    private Long createdAt;

    private Long updatedAt;

}
