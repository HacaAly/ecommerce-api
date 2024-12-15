package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.entity.Order;
import com.hikadobushido.ecommerce_java.model.PaymentNotification;
import com.hikadobushido.ecommerce_java.model.PaymentResponse;

public interface PaymentService {

    PaymentResponse create(Order order);

    PaymentResponse findByPaymentId(String paymentId);

    boolean verifyByPaymentId(String paymentId);

    void handleNotification(PaymentNotification paymentNotification);

}
