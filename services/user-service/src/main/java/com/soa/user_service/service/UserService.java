package com.soa.user_service.service;

import com.soa.user_service.dto.RequestCreateUser;
import com.soa.user_service.entity.User;
import com.soa.user_service.repository.UserRepository;
import com.soa.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // Register a new user
    public User registerUser(RequestCreateUser requestCreateUser) {
        if (userRepository.existsByUsername(requestCreateUser.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User user = User.builder()
                .name(requestCreateUser.getName())
                .email(requestCreateUser.getEmail())
                .username(requestCreateUser.getUsername())
                .password(passwordEncoder.encode(requestCreateUser.getPassword()))
                .build();
        return userRepository.save(user);
    }
    
    // Validate a token
    public String validateToken(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null || !jwtUtil.validateToken(token, username)) {
            throw new IllegalArgumentException("Invalid token");
        }
        return "Token is valid";
    }
    
    // Refresh a token
    public String refreshToken(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null || !jwtUtil.validateToken(token, username)) {
            throw new IllegalArgumentException("Invalid token");
        }
        return jwtUtil.generateAccessToken(username);
    }
    
    // Authenticate a user
    public Map<String, String> authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", jwtUtil.generateAccessToken(username));
        response.put("refreshToken", jwtUtil.generateRefreshToken(username));
        return response;
    }
}

