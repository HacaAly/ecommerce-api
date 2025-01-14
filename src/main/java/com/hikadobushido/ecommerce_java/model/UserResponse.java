package com.hikadobushido.ecommerce_java.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hikadobushido.ecommerce_java.entity.Role;
import com.hikadobushido.ecommerce_java.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class UserResponse implements Serializable {
    private Long userId;
    private String username;
    private String email;
    private boolean enable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> roles;

    //Helper Function
    public static UserResponse fromUserAndRoles(User user, List<Role> role) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enable(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(role.stream().map(Role::getName).toList())
                .build();
    }
}
