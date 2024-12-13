package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.ShippingOrderRequest;
import com.hikadobushido.ecommerce_java.model.ShippingOrderResponse;
import com.hikadobushido.ecommerce_java.model.ShippingRateRequest;
import com.hikadobushido.ecommerce_java.model.ShippingRateResponse;
import java.math.BigDecimal;

public interface ShippingService {

    ShippingRateResponse calculateShippingRate(ShippingRateRequest request);

    ShippingOrderResponse createShippingOrder(ShippingOrderRequest request);

    String generateAwbNumber(Long orderId);

    BigDecimal calculateTotalWeight(Long orderId);
}