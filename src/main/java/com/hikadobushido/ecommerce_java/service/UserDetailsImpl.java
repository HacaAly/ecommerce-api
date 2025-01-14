package com.hikadobushido.ecommerce_java.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hikadobushido.ecommerce_java.common.exception.UserNotFoundException;
import com.hikadobushido.ecommerce_java.entity.Role;
import com.hikadobushido.ecommerce_java.entity.User;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.repository.RoleRepository;
import com.hikadobushido.ecommerce_java.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetailsService {

    private final String USER_CACHE_KEY = "cache:user:";
    private final String USER_ROLES_CACHE_KEY = "cache:user:roles:";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CacheService cacheService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userCacheKey = USER_CACHE_KEY + username;
        String rolesCacheKey = USER_ROLES_CACHE_KEY + username;

        Optional<User> userOpt = cacheService.get(userCacheKey, User.class);
        Optional<List<Role>> roleOpt = cacheService.get(rolesCacheKey, new TypeReference<List<Role>>() {
        });

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            return UserInfo.builder()
                    .roles(roleOpt.get())
                    .user(userOpt.get())
                    .build();
        }

        User user = userRepository.findByKeyword(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username : " + username));
        List<Role> roles = roleRepository.findByUserId(user.getUserId());

        UserInfo userInfo = UserInfo.builder()
                .roles(roles)
                .user(user)
                .build();
        cacheService.put(userCacheKey, user);
        cacheService.put(rolesCacheKey, roles);

        return userInfo;
    }
}
