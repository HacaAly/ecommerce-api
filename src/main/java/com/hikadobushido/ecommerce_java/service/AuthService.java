package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.AuthRequest;
import com.hikadobushido.ecommerce_java.model.UserInfo;

public interface AuthService {

    UserInfo authenticate(AuthRequest authRequest);
}
