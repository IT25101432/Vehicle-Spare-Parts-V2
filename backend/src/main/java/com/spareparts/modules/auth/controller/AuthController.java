package com.spareparts.modules.auth.controller;

import com.spareparts.core.dto.AuthResponse;
import com.spareparts.modules.auth.dto.LoginRequest;
import com.spareparts.modules.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody com.spareparts.modules.auth.dto.ChangePasswordRequest request, java.security.Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.changePassword(principal.getName(), request);
        return ResponseEntity.ok().build();
    }
}