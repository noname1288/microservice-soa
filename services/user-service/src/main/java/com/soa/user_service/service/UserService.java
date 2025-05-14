package com.soa.user_service.service;

import com.soa.user_service.dto.RequestCreateUser;
import com.soa.user_service.dto.ResponseMember;
import com.soa.user_service.entity.User;
import com.soa.user_service.repository.UserRepository;
import com.soa.user_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<String, String> validateToken(String token) {
        Map<String, String> response = new HashMap<>();
        try {
            String username = jwtUtil.extractUsername(token);
            if (username != null && jwtUtil.validateToken(token, username)) {
                response.put("success", "true");
                response.put("username", username);
                return response;
            }
        } catch (Exception e) {
            // Log the error if needed
        }
        response.put("success", "false");
        return response;
    }

    // Refresh a token
    public Map<String, String> refreshToken(String refreshToken) {
        Map<String, String> response = new HashMap<>();

        try {
            // Trích xuất username từ refresh token
            String username = jwtUtil.extractUsername(refreshToken);
            System.out.println("OK1");
            // Kiểm tra token có hợp lệ không
            if (username == null || !jwtUtil.validateToken(refreshToken, username)) {
                response.put("success", "false");
                response.put("message", "Invalid or expired refresh token");
                return response;
            }
            System.out.println("OK22");
            // Kiểm tra loại token (nếu cần)
            Claims claims = jwtUtil.extractClaims(refreshToken);
            if (!"refresh".equals(claims.get("type"))) {
                response.put("success", "false");
                response.put("message", "Invalid token type");
                return response;
            }
            System.out.println("OK3");
            // Tạo access token mới
            String newAccessToken = jwtUtil.generateAccessToken(username);

            response.put("success", "true");
            response.put("accessToken", newAccessToken);
            return response;
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về phản hồi lỗi
            System.out.println("OK5");
            response.put("success", "false");
            response.put("message", "An error occurred while refreshing token: " + e.getMessage());
            return response;
            
        }
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
        response.put("userId", String.valueOf(user.getId()));
        return response;
    }

    public Map<String, Object> getListMember(List<Long> listIdUser) {
        List<User> users = userRepository.findAllById(listIdUser);
        
        List<ResponseMember> members = users.stream()
                .map(user -> new ResponseMember(user.getId(), user.getName(), user.getEmail()))  // Đổi thành MemberDTO
                .collect(Collectors.toList());
        return Map.of(
            "success", "true",
            "members", members
        );
    }

    public Map<String, Object> getAllUser() {
        List<User> users = userRepository.findAll();
        List<ResponseMember> members = users.stream()
                .map(user -> new ResponseMember(user.getId(), user.getName(), user.getEmail()))  // Đổi thành MemberDTO
                .collect(Collectors.toList());
        return Map.of(
            "success", "true",
            "users", members
        );
    }
}
