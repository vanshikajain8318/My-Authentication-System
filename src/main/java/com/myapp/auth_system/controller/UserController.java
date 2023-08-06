package com.myapp.auth_system.controller;

import com.myapp.auth_system.entity.User;
import com.myapp.auth_system.service.JwtService;
import com.myapp.auth_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private JwtService jwtService;

    @EventListener(ContextRefreshedEvent.class)
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Admin panel");
    }

}
