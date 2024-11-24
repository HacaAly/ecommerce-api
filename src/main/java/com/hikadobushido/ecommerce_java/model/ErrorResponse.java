package com.hikadobushido.ecommerce_java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private String message;
    private LocalDateTime timeStamp;
}
