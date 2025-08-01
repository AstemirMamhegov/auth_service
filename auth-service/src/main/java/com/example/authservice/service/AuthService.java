package com.example.authservice.service;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
    AuthResponse refresh(String refreshToken);
}
