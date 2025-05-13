package com.soa.user_service.controller;

import com.soa.user_service.dto.RequestCreateUser;
import com.soa.user_service.dto.RequestGetListMember;
import com.soa.user_service.dto.RequestLogin;
import com.soa.user_service.dto.RequestRefresh;
import com.soa.user_service.dto.RequestValidate;
import com.soa.user_service.entity.User;
import com.soa.user_service.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RequestCreateUser requestCreateUser) {
        return userService.registerUser(requestCreateUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin) {
        String username = requestLogin.getUsername();
        String password = requestLogin.getPassword();
        return ResponseEntity.ok(userService.authenticate(username, password));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refresh(@RequestBody RequestRefresh request) {        
        return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody RequestValidate request) {
        return ResponseEntity.ok(userService.validateToken(request.getToken()));
    }

    @PostMapping("/list-member")
    public ResponseEntity<?> getListMember(@RequestBody RequestGetListMember request) {
        return ResponseEntity.ok(userService.getListMember(request.getListIdUser()));
    }
}
