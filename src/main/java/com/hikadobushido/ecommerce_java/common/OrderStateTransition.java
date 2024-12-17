package com.hikadobushido.ecommerce_java.common;

import com.hikadobushido.ecommerce_java.model.OrderStatus;
import java.util.EnumMap;
import java.util.Set;

public class OrderStateTransition {

    // PENDING => [CANCELLED, PAID, PAYMENT_FAILED]
    // PENDING !=> SHIPPED
    private static final EnumMap<OrderStatus, Set<OrderStatus>> VALID_TRANSITIONS = new EnumMap<>(
            OrderStatus.class);

    static {
        VALID_TRANSITIONS.put(OrderStatus.PENDING,
                Set.of(OrderStatus.CANCELLED, OrderStatus.PAID, OrderStatus.PAYMENT_FAILED));
        VALID_TRANSITIONS.put(OrderStatus.PAID, Set.of(OrderStatus.SHIPPED));
        VALID_TRANSITIONS.put(OrderStatus.CANCELLED, Set.of());
        VALID_TRANSITIONS.put(OrderStatus.SHIPPED, Set.of());
        VALID_TRANSITIONS.put(OrderStatus.PAYMENT_FAILED, Set.of());
    }

    // PENDING & PAID => return true
    // PENDING & SHIPPED => return false
    public static boolean isValidTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        Set<OrderStatus> validNextStates = VALID_TRANSITIONS.get(currentStatus);
        if (validNextStates == null) {
            return false;
        }
        return validNextStates.contains(newStatus);
    }
}