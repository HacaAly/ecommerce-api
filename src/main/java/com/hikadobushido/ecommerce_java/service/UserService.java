package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.UserRegisterRequest;
import com.hikadobushido.ecommerce_java.model.UserResponse;
import com.hikadobushido.ecommerce_java.model.UserUpdateRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest registerRequest);
    UserResponse findById(long id);
    UserResponse findByKeyword(String keyword);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
