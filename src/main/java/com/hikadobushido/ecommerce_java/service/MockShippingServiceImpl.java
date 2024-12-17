package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.common.OrderStateTransition;
import com.hikadobushido.ecommerce_java.common.errors.ResourceNotFoundException;
import com.hikadobushido.ecommerce_java.entity.Order;
import com.hikadobushido.ecommerce_java.entity.OrderItem;
import com.hikadobushido.ecommerce_java.entity.Product;
import com.hikadobushido.ecommerce_java.model.OrderStatus;
import com.hikadobushido.ecommerce_java.model.ShippingOrderRequest;
import com.hikadobushido.ecommerce_java.model.ShippingOrderResponse;
import com.hikadobushido.ecommerce_java.model.ShippingRateRequest;
import com.hikadobushido.ecommerce_java.model.ShippingRateResponse;
import com.hikadobushido.ecommerce_java.repository.OrderItemRepository;
import com.hikadobushido.ecommerce_java.repository.OrderRepository;
import com.hikadobushido.ecommerce_java.repository.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockShippingServiceImpl implements
        ShippingService {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(10000);
    private static final BigDecimal RATE_PER_KG = BigDecimal.valueOf(2500);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public ShippingRateResponse calculateShippingRate(ShippingRateRequest request) {
        // shipping_fee = base_rate + (weight * rate per kg);
        BigDecimal shippingFee = BASE_RATE.add(
                        request.getTotalWeightInGrams().divide(BigDecimal.valueOf(1000)).multiply(RATE_PER_KG))
                .setScale(2, RoundingMode.HALF_UP);

        String estimatedDeliveryTime = "3 - 5 hari kerja";
        return ShippingRateResponse.builder()
                .shippingFee(shippingFee)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .build();
    }

    @Override
    public ShippingOrderResponse createShippingOrder(ShippingOrderRequest request) {
        String awbNumber = generateAwbNumber(request.getOrderId());

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id " + request.getOrderId() + " not found"));

        if (!OrderStateTransition.isValidTransition(order.getStatus(), OrderStatus.SHIPPED)) {
            throw new IllegalStateException(
                    "Invalid order status transition from " + order.getStatus() + " to SHIPPED");
        }

        order.setStatus(OrderStatus.SHIPPED);
        order.setAwbNumber(awbNumber);
        orderRepository.save(order);

        BigDecimal shippingFee = BASE_RATE.add(
                        request.getTotalWeightInGrams().divide(BigDecimal.valueOf(1000)).multiply(RATE_PER_KG))
                .setScale(2, RoundingMode.HALF_UP);

        String estimatedDeliveryTime = "3 - 5 hari kerja";

        return ShippingOrderResponse.builder()
                .awbNumber(awbNumber)
                .shippingFee(shippingFee)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .build();
    }

    @Override
    public String generateAwbNumber(Long orderId) {
        Random random = new Random();
        String prefix = "AWB";
        return String.format("%s%011d", prefix, random.nextInt(100000000));
    }


    @Override
    public BigDecimal calculateTotalWeight(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(orderItem -> {
                    Product product = productRepository.findById(orderItem.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Product not found with id " + orderItem.getProductId()));

                    BigDecimal totalWeight = product.getWeight()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                    return totalWeight;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}