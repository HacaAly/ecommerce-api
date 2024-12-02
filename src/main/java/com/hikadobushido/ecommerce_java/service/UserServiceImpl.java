package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.common.errors.*;
import com.hikadobushido.ecommerce_java.entity.Role;
import com.hikadobushido.ecommerce_java.entity.User;
import com.hikadobushido.ecommerce_java.entity.UserRole;
import com.hikadobushido.ecommerce_java.model.UserRegisterRequest;
import com.hikadobushido.ecommerce_java.model.UserResponse;
import com.hikadobushido.ecommerce_java.model.UserUpdateRequest;
import com.hikadobushido.ecommerce_java.repository.RoleRepository;
import com.hikadobushido.ecommerce_java.repository.UserRepository;
import com.hikadobushido.ecommerce_java.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse register(UserRegisterRequest registerRequest) {
        if (    existsByUsername(registerRequest.getUsername())){
            throw new UsernameAlreadyExistsException("Username is already taken");
        }
        if (existsByEmail(registerRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email is already taken");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordConfirmation())) {
            throw new BadRequestException("Password confirmation doesn't match");
        }

        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .enabled(true)
                .password(encodePassword)
                .build();

        userRepository.save(user);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role not found"));

        UserRole userRoleRelation = UserRole.builder()
                .id(new UserRole.UserRoleId(user.getUserId(), userRole.getRoleId()))
                .build();

        userRoleRepository.save(userRoleRelation);

        return UserResponse.fromUserAndRoles(user, List.of(userRole));
    }

    @Override
    public UserResponse findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));

        List<Role> roles = roleRepository.findByUserId(id);

        return UserResponse.fromUserAndRoles(user, roles);
    }

    @Override
    public UserResponse findByKeyword(String keyword) {
        User user = userRepository.findByKeyword(keyword)
                .orElseThrow(() -> new UserNotFoundException("User not found with user/email : " + keyword));
        List<Role> roles = roleRepository.findByUserId(user.getUserId());

        return UserResponse.fromUserAndRoles(user , roles);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));

        if (request.getCurrentPassword() != null && request.getNewPassword() != null) {
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new InvalidPasswordException("Current password is incorrect");
            }

            String encodedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(encodedPassword);
        }

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (existsByUsername(request.getUsername())) {
                throw new UsernameAlreadyExistsException(
                        "Username " + request.getUsername() + " is already taken"
                );
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && request.getEmail().equals(user.getEmail())) {
            if (existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException(
                        "Email " + request.getEmail() + " is already taken"
                );
            }
            user.setEmail(request.getEmail());
        }

        userRepository.save(user);
        List<Role> roles = roleRepository.findByUserId(user.getUserId());
        return UserResponse.fromUserAndRoles(user, roles);
    }

    // Pake @Transactional karena merubah data
    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));

        userRoleRepository.deleteByIdUserId(id);

        userRepository.delete(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
