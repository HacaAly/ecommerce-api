package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.model.PaymentNotification;
import com.hikadobushido.ecommerce_java.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    // POST host/api/v1/webhook/xendit
    @PostMapping("/xendit")
    public ResponseEntity<String> handleXenditWebhook(
            @RequestBody PaymentNotification paymentNotification
    ) {
        paymentService.handleNotification(paymentNotification);
        return ResponseEntity.ok("OK");
    }
}