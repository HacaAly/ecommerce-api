package com.hikadobushido.ecommerce_java.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;

import com.hikadobushido.ecommerce_java.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class ShippingRateRequest {

    private Address fromAddress;
    private Address toAddress;
    private BigDecimal totalWeightInGrams;

    @Data
    @Builder
    public static class Address {

        private String streetAddress;
        private String city;
        private String state;
        private String postalCode;
    }

    public static Address fromUserAddress(UserAddress userAddress) {
        return Address.builder()
                .streetAddress(userAddress.getStreetAddress())
                .city(userAddress.getCity())
                .state(userAddress.getState())
                .postalCode(userAddress.getPostalCode())
                .build();
    }
}