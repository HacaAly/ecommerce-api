package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.common.exception.ForbiddenAccessException;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.model.UserResponse;
import com.hikadobushido.ecommerce_java.model.UserUpdateRequest;
import com.hikadobushido.ecommerce_java.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserResponse userResponse = UserResponse.fromUserAndRoles(userInfo.getUser(),
                userInfo.getRoles());
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UserUpdateRequest updateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        if (userInfo.getUser().getUserId() != id && !userInfo.getAuthorities().contains("ROLE_ADMIN")) {
            throw new ForbiddenAccessException(
                    "user " + userInfo.getUsername() + " not allowed to update");
        }
        UserResponse updatedUser = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        if (userInfo.getUser().getUserId() != id && !userInfo.getAuthorities().contains("ROLE_ADMIN")) {
            throw new ForbiddenAccessException(
                    "user " + userInfo.getUsername() + " not allowed to delete");
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}