package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.UserInfo;

public interface JwtService {

    String generateToken(UserInfo userInfo);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);

}
