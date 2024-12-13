package com.hikadobushido.ecommerce_java.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class ShippingOrderRequest {

    private Long orderId;
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
}