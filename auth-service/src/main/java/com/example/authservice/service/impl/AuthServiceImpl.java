package com.example.authservice.service.impl;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;
import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements com.example.authservice.service.AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthResponse register(AuthRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailConfirmed(false);
        user.setRoles(Set.of(/* default role */));

        userRepository.save(user);
        return jwtProvider.generateTokens(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtProvider.generateTokens(user);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        return jwtProvider.refreshTokens(refreshToken);
    }
}
