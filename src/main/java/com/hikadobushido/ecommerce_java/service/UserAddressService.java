package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.UserAddressRequest;
import com.hikadobushido.ecommerce_java.model.UserAddressResponse;
import java.util.List;

public interface UserAddressService {

    UserAddressResponse create(Long userId, UserAddressRequest request);

    List<UserAddressResponse> findByUserId(Long userId);

    UserAddressResponse findById(Long id);

    UserAddressResponse update(Long addressId, UserAddressRequest request);

    void delete(Long addressId);

    UserAddressResponse setDefaultAddress(Long userId, Long addressId);

}