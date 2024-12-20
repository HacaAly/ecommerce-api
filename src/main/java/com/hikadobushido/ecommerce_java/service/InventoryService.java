package com.hikadobushido.ecommerce_java.service;

import java.util.Map;

public interface InventoryService {

    boolean checkAndLockInventory(Map<Long, Integer> productQuantities);

    void decreaseQuantity(Map<Long, Integer> productQuantities);

    void increaseQuantity(Map<Long, Integer> productQuantities);
}
