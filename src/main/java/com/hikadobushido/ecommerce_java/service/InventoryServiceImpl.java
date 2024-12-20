package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.common.exception.InventoryException;
import com.hikadobushido.ecommerce_java.entity.Product;
import com.hikadobushido.ecommerce_java.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public boolean checkAndLockInventory(Map<Long, Integer> productQuantities) {

        for (Map.Entry<Long, Integer> entry: productQuantities.entrySet()) {
            Product product = productRepository.findByIdWithPessimisticLock(entry.getKey())
                    .orElseThrow(() -> new InventoryException("Product with id " + entry.getKey() + " not found"));

            if (product.getStockQuantity() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void decreaseQuantity(Map<Long, Integer> productQuantities) {
        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findByIdWithPessimisticLock(entry.getKey())
                    .orElseThrow(
                            () -> new InventoryException("Product with id " + entry.getKey() + " is not found"));

            if (product.getStockQuantity() < entry.getValue()) {
                throw new InventoryException("Insufficient inventory for product " + entry.getKey());
            }

            Integer newQuantity = product.getStockQuantity() - entry.getValue();
            product.setStockQuantity(newQuantity);
            productRepository.save(product);
        }
    }

    @Override
    @Transactional
    public void increaseQuantity(Map<Long, Integer> productQuantities) {

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findByIdWithPessimisticLock(entry.getKey())
                    .orElseThrow(() -> new InventoryException("Product with id " + entry.getKey() + " not found"));

            Integer newQuantity = product.getStockQuantity() + entry.getValue();
            product.setStockQuantity(newQuantity);
            productRepository.save(product);
        }
    }
}
