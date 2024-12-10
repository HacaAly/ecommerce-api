package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.CartItemResponse;

import java.util.List;

public interface CartService {

    /*
    addItemToCart: Metode ini digunakan untuk menambahkan item ke keranjang belanja. Jadi, parameter yang dibutuhkan adalah:
    - userId: ID pengguna yang ingin menambahkan item.
    - productId: ID produk yang akan ditambahkan.
    - quantity: Jumlah produk yang akan ditambahkan.
     */
    void addItemToCart(Long userId, Long productId, int quantity);

    /*
    updateCartItemQuantity: Metode ini digunakan untuk mengubah jumlah item dalam keranjang. Parameter yang dibutuhkan adalah:
    -  userId: ID pengguna.
    - productId: ID produk yang ingin diubah jumlahnya.
    - quantity: Jumlah baru untuk produk tersebut.
     */
    void updateCartItemQuantity(Long userId, Long productId, int quantity);

    /*
    removeItemFromCart: Metode ini digunakan untuk menghapus item dari keranjang. Parameter yang dibutuhkan adalah:
    - userId: ID pengguna.
    - cartItemId: ID item yang akan dihapus.
     */
    void removeItemFromCart(Long userId, Long cartItemId);

    /*
    clearCart: Metode ini digunakan untuk mengosongkan seluruh keranjang belanja pengguna. Parameter yang dibutuhkan adalah:
    - userId: ID pengguna.
     */
    void clearCart(Long userId);

    /*
    getCartItem: Metode ini digunakan untuk mendapatkan daftar item dalam keranjang pengguna. Parameter yang dibutuhkan adalah:
    - userId: ID pengguna.
     */
    List<CartItemResponse> getCartItems(Long userId);

}
