package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.model.UserAddressRequest;
import com.hikadobushido.ecommerce_java.model.UserAddressResponse;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.service.UserAddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("address")
@SecurityRequirement(name = "Bearer")
public class AddressController {

    private final UserAddressService userAddressService;

    @PostMapping
    public ResponseEntity<UserAddressResponse> create(@Valid @RequestBody UserAddressRequest addressRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserAddressResponse response = userAddressService.create(userInfo.getUser().getUserId(), addressRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserAddressResponse>> findAddressByUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        List<UserAddressResponse> addressResponse = userAddressService.findByUserId(userInfo.getUser().getUserId());
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<UserAddressResponse> get(@PathVariable Long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserAddressResponse userAddressResponse = userAddressService.findById(addressId);
        return ResponseEntity.ok(userAddressResponse);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<UserAddressResponse> update(
            @Valid
            @PathVariable Long addressId,
            @RequestBody UserAddressRequest addressRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserAddressResponse addressResponse = userAddressService.update(addressId, addressRequest);

        return ResponseEntity.ok(addressResponse);

    }

    @DeleteMapping("{addressId}")
    public ResponseEntity<Void> delete (@PathVariable Long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        userAddressService.delete(addressId);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("{addressId}/set-default")
    public ResponseEntity<UserAddressResponse> setDefaultAddress(@PathVariable Long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserAddressResponse addressResponse = userAddressService.setDefaultAddress(userInfo.getUser().getUserId(), addressId );
        return ResponseEntity.ok(addressResponse);

    }

}
