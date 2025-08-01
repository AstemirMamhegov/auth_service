package com.example.authservice.controller;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;
import com.example.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        return ResponseEntity.ok(auth.getPrincipal());
    }
}
