package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.model.AuthRequest;
import com.hikadobushido.ecommerce_java.model.AuthResponse;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.model.UserRegisterRequest;
import com.hikadobushido.ecommerce_java.model.UserResponse;
import com.hikadobushido.ecommerce_java.service.AuthService;
import com.hikadobushido.ecommerce_java.service.JwtService;
import com.hikadobushido.ecommerce_java.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    // POST /api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest authRequest
    ) {
        UserInfo userInfo = authService.authenticate(authRequest);
        String token = jwtService.generateToken(userInfo);
        AuthResponse authResponse = AuthResponse.fromUserInfo(userInfo, token);

        return ResponseEntity.ok(authResponse);
    }

    // POST /api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRegisterRequest registerRequest
    ) {
        UserResponse userResponse = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userResponse);
    }
}
