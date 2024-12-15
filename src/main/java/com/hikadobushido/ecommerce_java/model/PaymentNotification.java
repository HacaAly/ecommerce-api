package com.hikadobushido.ecommerce_java.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class PaymentNotification {
    private String id;
    private BigDecimal amount;
    private String status;
    private Instant created;
    private boolean isHigh;
    private Instant paidAt;
    private Instant updated;
    private String userId;
    private String currency;
    private String paymentId;
    private String description;
    private String externalId;
    private BigDecimal paidAmount;
    private String payerEmail;
    private String ewalletType;
    private String merchantName;
    private String paymentMethod;
    private String paymentChannel;
    private String paymentMethodId;


}
